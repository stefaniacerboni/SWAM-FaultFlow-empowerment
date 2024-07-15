package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.analysis.PetriNetAnalyzer;
import it.unifi.stlab.analysis.PetriNetReducer;
import it.unifi.stlab.faultflow.businessLogic.controller.propagation.ErrorModeController;
import it.unifi.stlab.faultflow.businessLogic.exception.NoEntityFoundException;
import it.unifi.stlab.faultflow.dao.ErrorModeDao;
import it.unifi.stlab.faultflow.dao.FailureModeDao;
import it.unifi.stlab.faultflow.dao.PropagationPortDao;
import it.unifi.stlab.faultflow.dao.SystemDao;
import it.unifi.stlab.faultflow.dto.analysis.RewardDto;
import it.unifi.stlab.faultflow.dto.analysis.TransientAnalysisDto;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;
import it.unifi.stlab.faultflow.model.operational.ConcreteSystem;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;
import org.json.JSONObject;
import org.oristool.models.stpn.RewardRate;
import org.oristool.models.stpn.TransientSolution;
import org.oristool.models.stpn.trees.DeterministicEnablingState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
@Path("/analysis")
public class AnalysisEndpoint {
    @Inject
    SystemDao systemDao;
    @Inject
    FailureModeDao failureModeDao;
    @Inject
    ErrorModeController errorModeController;
    @Inject
    PropagationPortDao propagationPortDao;

    @GET
    @Path("/analysis")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response analysis(@QueryParam("uuid") String systemUUID,
                             @QueryParam("time") int time, @QueryParam("error") double error,
                             @QueryParam("step") double step,
                             @QueryParam("failure") String failureID,
                             @QueryParam("concreteComponent") String concreteComponentSerialNumber,
                             @QueryParam("threshold") Integer threshold,
                             @QueryParam("placeName") String placeName) {
        System system = systemDao.getSystemById(UUID.fromString(systemUUID));
        List<Component> components = system.getComponents();
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(system);

        TransientAnalysisDto TransientAnalysisDto = new TransientAnalysisDto(systemUUID, time, step);

        if (failureID == null) {
            List<FailureMode> failureModes = new ArrayList<>();
            List<String> failureNames = new ArrayList<>();

            for (Component component : components) {
                String serial = component.getName();

                component.getErrorModes().forEach(errorMode -> {
                    failureModes.add(errorMode.getOutgoingFailure());
                    failureNames.add(errorMode.getOutgoingFailure().getDescription().trim() + "_" + serial);
                });
            }

            Map<String, String> exoFaultsMap = PetriNetAnalyzer.createExoFaultsMap(components, failureNames);

            for (String failure : failureNames) {
                RewardDto rewardDto = new RewardDto(failure);
                List<String> contributingPlaces = getThreatChainEntityNames(failureModes.get(failureNames.indexOf(failure)));

                PetriNetReducer petriNetReducer = new PetriNetReducer(pnt.getPetriNet(), pnt.getMarking());
                petriNetReducer.reduce(contributingPlaces);
                PetriNetAnalyzer petriNetAnalyzer = new PetriNetAnalyzer(petriNetReducer.getPetriNet(),
                        petriNetReducer.getMarking());

                Map<String, List<Double>> rewards =
                        petriNetAnalyzer.calculateFailureRewards(new ArrayList<>(Collections.singleton(failure)), new BigDecimal(time), new BigDecimal(step), new BigDecimal(error), exoFaultsMap);

                rewards.get(failure).forEach(rewardDto::addValue);

                TransientAnalysisDto.addReward(rewardDto);
            }
        } else {
            FailureMode analyzedFailureMode;
            String failureName;

            try {
                analyzedFailureMode = failureModeDao.findById(UUID.fromString(failureID));
                failureName = analyzedFailureMode.getDescription().trim();
            } catch (NoEntityFoundException nfe) {
                return noEntityFoundResponse(nfe.getEntityClass(), nfe.getEntityExternalID());
            }

            List<String> contributingPlaces = getThreatChainEntityNames(analyzedFailureMode);
            PetriNetReducer petriNetReducer = new PetriNetReducer(pnt.getPetriNet(), pnt.getMarking());
            petriNetReducer.reduce(contributingPlaces);

            failureName += "_" + concreteComponentSerialNumber;
            if(placeName != null)
                failureName = placeName;

            PetriNetAnalyzer petriNetAnalyzer = new PetriNetAnalyzer(petriNetReducer.getPetriNet(), petriNetReducer.getMarking());

            TransientSolution<DeterministicEnablingState, RewardRate> rewards =
                    petriNetAnalyzer.regenerativeTransient(failureName, new BigDecimal(time), new BigDecimal(step), new BigDecimal(error));

            RewardDto RewardDto = new RewardDto(failureName);

            for (int index = 0; index < rewards.getSolution().length; index++) {
                RewardDto.addValue(rewards.getSolution()[index][0][0]);
            }

            TransientAnalysisDto.addReward(RewardDto);

            if (threshold != null) {
                TransientAnalysisDto.setHasFailed(RewardDto.getValues().get(RewardDto.getValues().size() - 1) >= threshold);
            }
        }

        return Response.ok(TransientAnalysisDto).build();
    }

    /**
     * Metodo che, data una failure mode in ingresso, ritorna una lista di stringhe che rappresenta l'insieme dei nomi
     * dei posti che contribuiscono a tale fallimento
     */
    private List<String> getThreatChainEntityNames(FailureMode failureMode) {
        List<String> contributingPlaces = new ArrayList<>();

        ErrorMode errorMode = errorModeController.findByFailureID(failureMode.getUuid().toString());
        contributingPlaces.add(errorMode.getName().trim());

        for (FaultMode faultMode : errorMode.getInputFaultModes()) {
            if (faultMode instanceof EndogenousFaultMode) {
                contributingPlaces.add(faultMode.getName().trim());
            } else {
                List<PropagationPort> propagationPorts = propagationPortDao.getPropagationPortsByExoFaultMode(faultMode.getUuid());

                for (PropagationPort propagationPort : propagationPorts) {
                    contributingPlaces.addAll(getThreatChainEntityNames(propagationPort.getPropagatedFailureMode()));
                }
            }
        }

        return contributingPlaces;
    }

    private Response noEntityFoundResponse(String entityClass, String entityID) {
        JSONObject response = new JSONObject();

        response.put("status", "error")
                .put("error", "NoEntityFound")
                .put("entityClass", entityClass)
                .put("entityID", entityID);

        return Response.ok(response.toString()).status(Response.Status.NOT_FOUND).build();
    }
}
