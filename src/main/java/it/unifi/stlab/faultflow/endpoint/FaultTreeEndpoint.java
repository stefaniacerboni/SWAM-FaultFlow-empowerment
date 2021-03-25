package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.dao.ErrorModeDao;
import it.unifi.stlab.faultflow.dao.PropagationPortDao;
import it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree.*;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;
import it.unifi.stlab.faultflow.model.knowledge.propagation.operators.KofN;
import it.unifi.stlab.faultflow.model.knowledge.propagation.operators.Operator;
import it.unifi.stlab.faultflow.model.operational.Error;
import it.unifi.stlab.launcher.systembuilder.PollutionMonitorPreliminaryDesignBuiler;
import it.unifi.stlab.launcher.systembuilder.SimpleSystem02Builder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/ft")
public class FaultTreeEndpoint {
    @Inject
    PropagationPortDao propagationPortDao = new PropagationPortDao();
    @Inject
    ErrorModeDao errorModeDao = new ErrorModeDao();

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaultTree(@QueryParam("errorMode_uuid") String errorModeuuid){
//        Component component = PollutionMonitorPreliminaryDesignBuiler.getInstance().getSystem().getComponent("MapReduceProcessorPM10");
//        ErrorMode errorMode = component.getErrorModes().get(0);
//        errorModeDao.getErrorModeById(errorMode.getUuid());
        InputFaultTreeDto faultTreeDto = getFaultTreeFromErrorMode(errorModeDao.getErrorModeById(UUID.fromString(errorModeuuid)));
        return Response.ok(faultTreeDto).build();
    }


    private InputFaultTreeDto getFaultTreeFromErrorMode(ErrorMode errorMode){
        InputFaultTreeDto inputFaultTreeDto = new InputFaultTreeDto();
        List<String> topEvents = new ArrayList<>();
        topEvents.add(errorMode.getOutgoingFailure().getUuid().toString());
        List<InputNodeDto> nodes = new ArrayList<>();
        List<InputParentingDto> parentings = new ArrayList<>();

        //Create Failure Node
        InputNodeDto topNode = new InputNodeDto();
        topNode.setExternalId(errorMode.getOutgoingFailure().getUuid().toString());
        topNode.setLabel(errorMode.getOutgoingFailure().getDescription());
        topNode.setNodeType(NodeType.FAILURE);
        topNode.setPdf(errorMode.getTimetofailurePDFToString());

        nodes.add(topNode);

        unfoldBooleanExpression(errorMode.getActivationFunction(), topNode.getExternalId(),
                nodes, parentings, errorMode.getName());


        inputFaultTreeDto.setNodes(nodes);
        inputFaultTreeDto.setParentings(parentings);
        inputFaultTreeDto.setTopEvents(topEvents);
        return inputFaultTreeDto;
    }

    private void unfoldBooleanExpression(BooleanExpression booleanExpression, String parent,
                                         List<InputNodeDto> nodes, List<InputParentingDto> parentings,
                                         String label){
        InputParentingDto parenting = new InputParentingDto();
        parenting.setParentId(parent);
        switch (booleanExpression.getClass().getSimpleName()){
            case "EndogenousFaultMode":
                InputNodeDto basicEndFault = new InputNodeDto();
                EndogenousFaultMode endogenousFaultMode = (EndogenousFaultMode) booleanExpression;
                basicEndFault.setExternalId(endogenousFaultMode.getUuid().toString());
                basicEndFault.setLabel(endogenousFaultMode.getName());
                basicEndFault.setNodeType(NodeType.BASIC_EVENT);
                basicEndFault.setPdf(endogenousFaultMode.getArisingPDFToString());
                nodes.add(basicEndFault);

                parenting.setChildId(basicEndFault.getExternalId());
                parentings.add(parenting);
                break;
            case "ExogenousFaultMode":
                //non si fa un nodo ma si decora il failure sottostante:
                // si deve andare a prendere il failure che lo propaga e creare un AliasDto
                ExogenousFaultMode exogenousFaultMode = (ExogenousFaultMode) booleanExpression;
                PropagationPort propagationPort = propagationPortDao.getPropagationPortByExoFaultMode(exogenousFaultMode.getUuid());
                ErrorMode errorMode = errorModeDao.getErrorModeByFailureModeID(propagationPort.getPropagatedFailureMode().getUuid());
                InputNodeDto failureModeNode = new InputNodeDto();
                failureModeNode.setExternalId(errorMode.getOutgoingFailure().getUuid().toString());
                failureModeNode.setLabel(errorMode.getOutgoingFailure().getDescription());
                failureModeNode.setNodeType(NodeType.FAILURE);
                failureModeNode.setPdf(errorMode.getTimetofailurePDFToString());
                parenting.setChildId(failureModeNode.getExternalId());
                parentings.add(parenting);

                List<AliasDto> aliases = new ArrayList<>();
                AliasDto aliasDto = new AliasDto();
                aliasDto.setFaultName(exogenousFaultMode.getName());
                aliasDto.setRoutingProbability(propagationPort.getRoutingProbability().doubleValue());
                aliasDto.setComponentId(propagationPort.getAffectedComponent().getName());
                aliases.add(aliasDto);
                failureModeNode.setActsAs(aliases);

                nodes.add(failureModeNode);

                unfoldBooleanExpression(errorMode.getActivationFunction(), failureModeNode.getExternalId(),
                        nodes, parentings, errorMode.getName());
                break;

            default:
                //è un Operator
                assert booleanExpression instanceof Operator;
                Operator operator = (Operator) booleanExpression;
                InputNodeDto gate = new InputNodeDto();
                gate.setExternalId(parent+operator.getClass().getSimpleName()+operator.hashCode());
                gate.setLabel(label);
                gate.setNodeType(NodeType.GATE);
                gate.setGateType(GateType.fromString(operator.getClass().getSimpleName()));
                if(gate.getGateType()==GateType.KOUTOFN){
                    gate.setK(((KofN)operator).getK());
                    gate.setN(((KofN)operator).getN());
                }
                nodes.add(gate);

                parenting.setChildId(gate.getExternalId());
                parentings.add(parenting);
                for(BooleanExpression element : operator.getElements()) {
                    unfoldBooleanExpression(element, gate.getExternalId(), nodes, parentings, label);
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
	@Path("/petri")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPetriNet(InputFaultTreeDto inputFaultTreeDto){
		PetriNetTranslator pnt = new PetriNetTranslator();
		pnt.translate(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto));
		return Response.ok(FaultTreeMapper.petriNetToJSON(pnt.getPetriNet())).build();
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
