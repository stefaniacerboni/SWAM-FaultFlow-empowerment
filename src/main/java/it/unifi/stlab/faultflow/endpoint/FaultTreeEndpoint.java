package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.dao.ComponentDao;
import it.unifi.stlab.faultflow.dao.ErrorModeDao;
import it.unifi.stlab.faultflow.dao.PropagationPortDao;
import it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree.*;
import it.unifi.stlab.faultflow.exporter.XPNExporter;
import it.unifi.stlab.faultflow.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;
import it.unifi.stlab.faultflow.model.knowledge.propagation.operators.KofN;
import it.unifi.stlab.faultflow.model.knowledge.propagation.operators.Operator;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/ft")
public class FaultTreeEndpoint {
    @Inject
    PropagationPortDao propagationPortDao = new PropagationPortDao();
    @Inject
    ErrorModeDao errorModeDao = new ErrorModeDao();
    @Inject
    ComponentDao componentDao = new ComponentDao();

    @GET
    @Path("/from_error_mode/{errorMode_uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaultTreeFromErrorMode(@PathParam("errorMode_uuid") String errorModeuuid) {
        ErrorMode errorMode;
        if (errorModeuuid == null)
            throw new IllegalArgumentException("Please, specify ErroreMode's UUID as path Parameter!");
        else {
            errorMode = errorModeDao.getErrorModeById(UUID.fromString(errorModeuuid));
        }
        try {
            FaultTreeDto faultTreeDto = getFaultTreeFromErrorMode(errorMode);
            return Response.ok(faultTreeDto).build();
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("Error!");
        }

    }

    @GET
    @Path("/from_component/{component_uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaultTreesFromComponent(@PathParam("component_uuid") String componentuuid) {
        //Component component = PollutionMonitorPreliminaryDesignBuiler.getInstance().getSystem().getComponent("MapReduceProcessorPM10");
        Component component;
        if (componentuuid == null)
            throw new IllegalArgumentException("Please, specify Component's UUID as query Parameter!");

        else {
            component = componentDao.getComponentById(UUID.fromString(componentuuid));
        }
        try {
            List<FaultTreeDto> faultTreeDtos = new ArrayList<>();
            for (ErrorMode errorMode : component.getErrorModes()) {
                FaultTreeDto faultTreeDto = getFaultTreeFromErrorMode(errorModeDao.getErrorModeById(errorMode.getUuid()));
                faultTreeDtos.add(faultTreeDto);
            }
            return Response.ok(faultTreeDtos).build();
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("Error in getting the FaultTree!");
        }

    }

    @POST
    @Path("/petri")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNet(FaultTreeDto faultTreeDto) {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = FaultTreeMapper.generateSystemFromFaultTree(faultTreeDto);
        //TODO nome del file generatore casualmente + path configurabile in maniera più elegante attraverso un file di properties
        File out = new File("PetriNet.xpn");
        try {
            pnt.translate(sys);
            XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
            return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
        } catch (FileNotFoundException fnf) {
            throw new NotFoundException("File not Found");
        } catch (JAXBException e) {
            throw new BadRequestException("There's been a problem with the conversion to XPN");
        } catch (Exception e) {
            throw new InternalServerErrorException("Unexpected Server Problem");
        }
    }

    private FaultTreeDto getFaultTreeFromErrorMode(ErrorMode errorMode) {
        FaultTreeDto faultTreeDto = new FaultTreeDto();
        List<String> topEvents = new ArrayList<>();
        topEvents.add(errorMode.getOutgoingFailure().getUuid().toString());
        List<NodeDto> nodes = new ArrayList<>();
        List<ParentingDto> parentings = new ArrayList<>();

        Component c = componentDao.getComponentByErrorModeUUID(errorMode.getUuid());

        //Create Failure Node
        NodeDto topNode = new NodeDto();
        topNode.setExternalId(errorMode.getOutgoingFailure().getUuid().toString());
        topNode.setLabel(errorMode.getOutgoingFailure().getDescription());
        topNode.setNodeType(NodeType.FAILURE);
        topNode.setComponentId(c.getName());
        topNode.setPdf(errorMode.getTimetofailurePDFToString());

        nodes.add(topNode);

        unfoldBooleanExpression(errorMode.getActivationFunction(), topNode.getExternalId(),
                nodes, parentings, errorMode.getName(), c.getName());


        faultTreeDto.setNodes(nodes);
        faultTreeDto.setParentings(parentings);
        faultTreeDto.setTopEvents(topEvents);
        return faultTreeDto;
    }

    private void unfoldBooleanExpression(BooleanExpression booleanExpression, String parent,
                                         List<NodeDto> nodes, List<ParentingDto> parentings,
                                         String label, String component) {
        ParentingDto parenting = new ParentingDto();
        parenting.setParentId(parent);
        switch (booleanExpression.getClass().getSimpleName()) {
            case "EndogenousFaultMode":
                NodeDto basicEndFault = new NodeDto();
                EndogenousFaultMode endogenousFaultMode = (EndogenousFaultMode) booleanExpression;
                basicEndFault.setExternalId(endogenousFaultMode.getUuid().toString());
                basicEndFault.setLabel(endogenousFaultMode.getName());
                basicEndFault.setNodeType(NodeType.BASIC_EVENT);
                basicEndFault.setPdf(endogenousFaultMode.getArisingPDFToString());
                basicEndFault.setComponentId(component);
                nodes.add(basicEndFault);

                parenting.setChildId(basicEndFault.getExternalId());
                parentings.add(parenting);
                break;
            case "ExogenousFaultMode":
                //non si fa un nodo ma si decora il failure sottostante:
                // si deve andare a prendere il failure che lo propaga e creare un AliasDto
                ExogenousFaultMode exogenousFaultMode = (ExogenousFaultMode) booleanExpression;
                //Estrae una lista di PropagationPort in cui compare il faultEsogeno. NB: uno stesso Fault Esogeno
                //può comparire in più righe della tabella PropagationPorts, perché può andare in ingresso a due componenti
                //diverse, ma sarà sempre propagato da uno ed un solo failure (il failure della lista di propagationPort
                //estratte sarà lo stesso, così come il campo ExoFault, cambierà solamente il Component, che ci serve per
                //creare gli AliasDTO

                List<PropagationPort> propagationPorts = propagationPortDao.getPropagationPortsByExoFaultMode(exogenousFaultMode.getUuid());
                ErrorMode errorMode = errorModeDao.getErrorModeByFailureModeID(propagationPorts.get(0).getPropagatedFailureMode().getUuid());
                Optional<NodeDto> failurePresent = nodes.stream().filter(x -> x.getExternalId().equals(errorMode.getOutgoingFailure().getUuid().toString())).findFirst();
                if (failurePresent.isPresent()) {
                    parenting.setChildId(failurePresent.get().getExternalId());
                    parentings.add(parenting);
                } else {
                    Component newComponent = componentDao.getComponentByErrorModeUUID(errorMode.getUuid());
                    NodeDto failureModeNode = new NodeDto();
                    failureModeNode.setExternalId(errorMode.getOutgoingFailure().getUuid().toString());
                    failureModeNode.setLabel(errorMode.getOutgoingFailure().getDescription());
                    failureModeNode.setNodeType(NodeType.FAILURE);
                    failureModeNode.setPdf(errorMode.getTimetofailurePDFToString());
                    failureModeNode.setComponentId(newComponent.getName());
                    parenting.setChildId(failureModeNode.getExternalId());
                    parentings.add(parenting);

                    List<AliasDto> aliases = new ArrayList<>();
                    for (PropagationPort propagationPort : propagationPorts) {
                        AliasDto aliasDto = new AliasDto();
                        aliasDto.setFaultName(exogenousFaultMode.getName());
                        aliasDto.setRoutingProbability(propagationPort.getRoutingProbability().doubleValue());
                        aliasDto.setComponentId(propagationPort.getAffectedComponent().getName());
                        aliases.add(aliasDto);
                    }

                    failureModeNode.setActsAs(aliases);
                    nodes.add(failureModeNode);

                    unfoldBooleanExpression(errorMode.getActivationFunction(), failureModeNode.getExternalId(),
                            nodes, parentings, errorMode.getName(), newComponent.getName());
                }
                break;

            default:
                //è un Operator
                assert booleanExpression instanceof Operator;
                Operator operator = (Operator) booleanExpression;
                NodeDto gate = new NodeDto();
                gate.setExternalId(parent + operator.getClass().getSimpleName() + operator.hashCode());
                gate.setLabel(label);
                gate.setNodeType(NodeType.GATE);
                gate.setGateType(GateType.fromString(operator.getClass().getSimpleName()));
                if (gate.getGateType() == GateType.KOUTOFN) {
                    gate.setK(((KofN) operator).getK());
                    gate.setN(((KofN) operator).getN());
                }
                gate.setComponentId(component);
                nodes.add(gate);

                parenting.setChildId(gate.getExternalId());
                parentings.add(parenting);
                for (BooleanExpression element : operator.getElements()) {
                    unfoldBooleanExpression(element, gate.getExternalId(), nodes, parentings, label, component);
                }

        }
    }
/*

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSystem(InputFaultTreeDto inputFaultTreeDto){
		return Response.ok(FaultTreeMapper.systemToOutputSystem(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto))).build();
	}

	@POST
	@Path("/xpn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPetriNetXPN(InputFaultTreeDto inputFaultTreeDto){
		PetriNetTranslator pnt = new PetriNetTranslator();
		System sys = FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto);
		pnt.translate(sys);
		File out = new File("prova.xpn");
		try {
			XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
			return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
		}
		catch (FileNotFoundException fnf ){
			throw new NotFoundException("File not Found");
		}
		catch(JAXBException e){
			throw new BadRequestException("There's been a problem with the conversion to XPN");
		}
		catch (Exception e){
			throw new InternalServerErrorException("Unexpected Server Problem");
		}
	}
*/

}
