package it.unifi.stlab.mapper;

import it.unifi.stlab.dto.inputsystemdto.faulttree.*;
import it.unifi.stlab.dto.petrinet.*;
import it.unifi.stlab.dto.system.*;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.knowledge.utils.PDFParser;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.util.*;
import java.util.stream.Collectors;

public class FaultTreeMapper {
    private static boolean isComponentInSystem(System system, String name) {
        return system.getComponents().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findAny().isPresent();
    }

    private static MetaComponent getComponentInSystem(System system, String name) {
        Optional<MetaComponent> mc = system.getComponents().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findAny();
        if (mc.isPresent())
            return mc.get();
        else {
            MetaComponent metaComponent = new MetaComponent(name);
            system.addComponent(metaComponent);
            return metaComponent;
        }
    }

    public static void decorateSystem(InputFaultTreeDto inputFaultTreeDto, System system ) {
        Queue<InputNodeDto> nodeToVisit = new LinkedList<>();
        HashMap<String, FaultMode> faultModes = new HashMap<>();
        HashMap<String, FailureMode> failureModes = new HashMap<>();
        String[] nodeNameSplitted;
        for (String topEvent : inputFaultTreeDto.getTopEvents()) {
            nodeToVisit.add(getNodeFromID(inputFaultTreeDto, topEvent));
        }
        while (!nodeToVisit.isEmpty()) {
            InputNodeDto parent = nodeToVisit.remove();
            List<InputParentingDto> parentings = getChildrenFromNodeID(inputFaultTreeDto, parent.getExternalId());
            for (InputParentingDto parenting : parentings) {
                InputNodeDto currentNode = getNodeFromID(inputFaultTreeDto, parenting.getChildId());

                if (currentNode.getNodeType() == NodeType.GATE) {

                    nodeNameSplitted = currentNode.getLabel().split("_");
                    MetaComponent mc = getComponentInSystem(system, nodeNameSplitted[0]);
                    if (!mc.isErrorModeNamePresent(currentNode.getExternalId())) {
                        ErrorMode errorMode = new ErrorMode(currentNode.getExternalId());
                        FailureMode fm = failureModes.get(parent.getExternalId());
                        if (fm != null)
                            errorMode.setOutGoingFailure(fm);
                        else {
                            errorMode.setOutGoingFailure(new FailureMode(parent.getExternalId()));
                            failureModes.put(errorMode.getOutgoingFailure().getDescription(), errorMode.getOutgoingFailure());
                        }
                        //errorMode.setOutGoingFailure(new FailureMode(parent.getExternalId()));
                        errorMode.setPDF(parent.getPdf());
                        getEnablingFunctionByNavigatingTree(inputFaultTreeDto, currentNode.getExternalId(), errorMode, faultModes, failureModes, nodeToVisit, system);
                        mc.addErrorMode(errorMode);
                    }
                } else if (currentNode.getNodeType() == NodeType.FAILURE) {
                    //add its children
                    nodeToVisit.add(currentNode);
                }
            }

        }
    }

    public static System generateSystemFromFaultTree(InputFaultTreeDto inputFaultTreeDto) {
        String[] nodeNameSplitted;
        System system=null;
        for(String topEvent : inputFaultTreeDto.getTopEvents()){
            nodeNameSplitted = topEvent.split("_");
            MetaComponent topComponent = new MetaComponent(nodeNameSplitted[0]);
            system = new System(nodeNameSplitted[0] + "_SYS");
            system.addComponent(topComponent);
            system.setTopLevelComponent(topComponent);
        }
        decorateSystem(inputFaultTreeDto, system);
        return system;
    }

    public static List<InputParentingDto> getChildrenFromNodeID(InputFaultTreeDto faultTree, String nodeID) {
        return faultTree.getParentings().stream().filter(x -> x.getParentId().equals(nodeID)).collect(Collectors.toList());
    }

    public static List<InputParentingDto> getParentsFromNodeID(InputFaultTreeDto faultTree, String nodeID) {
        return faultTree.getParentings().stream().filter(x -> x.getChildId().equals(nodeID)).collect(Collectors.toList());
    }

    public static InputNodeDto getNodeFromID(InputFaultTreeDto faultTree, String nodeID) {
        return faultTree.getNodes().stream().filter(x -> x.getExternalId().equalsIgnoreCase(nodeID)).findFirst().get();
    }

    public static String getEnablingFunctionByNavigatingTree(InputFaultTreeDto faultTreeDto, String currentNode, ErrorMode errorMode,
                                                             HashMap<String, FaultMode> faultModes, HashMap<String, FailureMode> failureModes,
                                                             Queue<InputNodeDto> nodeToVisit, System system) {
        StringBuilder be = new StringBuilder();
        InputNodeDto nodeDto = getNodeFromID(faultTreeDto, currentNode);
        switch (nodeDto.getGateType()) {
            case OR:
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    switch (child.getNodeType()) {
                        case GATE:
                            be.append("(" + getEnablingFunctionByNavigatingTree(faultTreeDto, child.getExternalId(), errorMode, faultModes, failureModes, nodeToVisit, system)).append(")||");
                            break;
                        case FAILURE:
                            FailureMode failureMode = new FailureMode(child.getExternalId());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String faultname = generateFailuresFaultName(child, nodeDto.getExternalId());
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            be.append(faultname).append("||");
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            String[] nodeNameSplitted;
                            nodeNameSplitted = failureMode.getDescription().split("_");
                            MetaComponent mc = getComponentInSystem(system, nodeNameSplitted[0]);
                            String currentComponent = currentNode.substring(0, currentNode.indexOf("_"));
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, getComponentInSystem(system, currentComponent)));
                            break;
                        default:
                            be.append(child.getExternalId()).append("||");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getExternalId());
                            endogenousFaultMode.setArisingPDF(child.getPdf());
                            errorMode.addInputFaultMode(endogenousFaultMode);
                            faultModes.put(child.getExternalId(), endogenousFaultMode);
                    }
                }
                be.delete(be.length() - 2, be.length());
                break;
            case AND:
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    switch (child.getNodeType()) {
                        case GATE:
                            be.append("(" + getEnablingFunctionByNavigatingTree(faultTreeDto, child.getExternalId(), errorMode, faultModes, failureModes, nodeToVisit, system)).append(")&&");
                            break;
                        case FAILURE:
                            FailureMode failureMode = new FailureMode(child.getExternalId());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String faultname = generateFailuresFaultName(child, nodeDto.getExternalId());
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            be.append(faultname).append("&&");
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            String[] nodeNameSplitted;
                            nodeNameSplitted = failureMode.getDescription().split("_");
                            MetaComponent mc = getComponentInSystem(system, nodeNameSplitted[0]);
                            String currentComponent = currentNode.substring(0, currentNode.indexOf("_"));
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, getComponentInSystem(system, currentComponent)));
                            break;
                        default:
                            be.append(child.getExternalId()).append("&&");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getExternalId());
                            endogenousFaultMode.setArisingPDF(child.getPdf());
                            errorMode.addInputFaultMode(endogenousFaultMode);
                            faultModes.put(child.getExternalId(), endogenousFaultMode);
                    }
                }
                be.delete(be.length() - 2, be.length());
                break;
            case KOUTOFN:
                be.append(nodeDto.getK()).append("/").append(nodeDto.getN()).append("(");
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    //hope they're all basic or failures
                    switch (child.getNodeType()) {
                        case FAILURE:
                            FailureMode failureMode = new FailureMode(child.getExternalId());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String faultname = generateFailuresFaultName(child, nodeDto.getExternalId());
                            be.append(faultname).append(",");
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            String[] nodeNameSplitted;
                            nodeNameSplitted = faultname.split("_");
                            MetaComponent mc = getComponentInSystem(system, nodeNameSplitted[0]);
                            String currentComponent = currentNode.substring(0, currentNode.indexOf("_"));
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, getComponentInSystem(system, currentComponent)));
                            break;
                        default:
                            be.append(child.getExternalId()).append(",");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getExternalId());
                            endogenousFaultMode.setArisingPDF(child.getPdf());
                            errorMode.addInputFaultMode(endogenousFaultMode);
                            faultModes.put(child.getExternalId(), endogenousFaultMode);
                    }
                }
                be.delete(be.length() - 1, be.length());
                be.append(")");
                break;
            default:
        }
        errorMode.setEnablingCondition(be.toString(), faultModes);
        return be.toString();
    }

    public static PetriNetDto petriNetToJSON(PetriNet net) {
        PetriNetDto outputNet = new PetriNetDto();
        List<PlaceDto> outputPlaces = new ArrayList<>();
        for (Place place : net.getPlaces()) {
            PlaceDto outputPlace = new PlaceDto();
            outputPlace.setName(place.getName());
            outputPlaces.add(outputPlace);
        }
        outputNet.setPlaces(outputPlaces);
        List<TransitionDto> outputTransitions = new ArrayList<>();
        List<PreconditionDto> outputPreconditions = new ArrayList<>();
        List<PostconditionDto> outputPostconditions = new ArrayList<>();
        for (Transition transition : net.getTransitions()) {
            TransitionDto outputTransition = new TransitionDto();
            outputTransition.setName(transition.getName());
            List<FeaturesDto> features = new ArrayList<>();
            if (transition.getFeature(EnablingFunction.class) != null) {
                EnablingFunctionDto enablingFunctionDto = new EnablingFunctionDto();
                enablingFunctionDto.setEnablingFunction(transition.getFeature(EnablingFunction.class).toString());
                features.add(enablingFunctionDto);
            }
            if (transition.getFeature(StochasticTransitionFeature.class) != null) {
                PDFDto pdf = new PDFDto();
                pdf.setPdf(PDFParser.parseStochasticTransitionFeatureToString(transition.getFeature(StochasticTransitionFeature.class)));
                features.add(pdf);
            }
            outputTransition.setFeatures(features);
            outputTransitions.add(outputTransition);

            for (Postcondition postcondition : net.getPostconditions(transition)) {
                PostconditionDto outputPostcondition = new PostconditionDto();
                outputPostcondition.setTransition(postcondition.getTransition().getName());
                outputPostcondition.setPlace(postcondition.getPlace().getName());
                outputPostconditions.add(outputPostcondition);
            }
            for (Precondition precondition : net.getPreconditions(transition)) {
                PreconditionDto outputPrecondition = new PreconditionDto();
                outputPrecondition.setTransition(precondition.getTransition().getName());
                outputPrecondition.setPlace(precondition.getPlace().getName());
                outputPreconditions.add(outputPrecondition);
            }
        }
        outputNet.setTransitions(outputTransitions);
        outputNet.setPreconditions(outputPreconditions);
        outputNet.setPostconditions(outputPostconditions);
        return outputNet;
    }

    public static OutputSystemDto systemToOutputSystem(System system) {
        OutputSystemDto outputSystemDto = new OutputSystemDto();
        outputSystemDto.setName(system.getName());
        List<MetaComponentDto> outputMetaComponents = new ArrayList<>();
        for (MetaComponent metaComponent : system.getComponents()) {
            MetaComponentDto outputMetaComponent = new MetaComponentDto();
            outputMetaComponent.setName(metaComponent.getName());
            List<CompositionPortDto> outputCompositionPorts = new ArrayList<>();
            List<ErrorModeDto> outputErrorModes = new ArrayList<>();
            List<PropagationPortDto> outputPropagationPorts = new ArrayList<>();
            for (ErrorMode errorMode : metaComponent.getErrorModes()) {
                ErrorModeDto outputErrorMode = new ErrorModeDto();
                outputErrorMode.setName(errorMode.getName());
                outputErrorMode.setActivationFunction(errorMode.getActivationFunction());
                outputErrorMode.setTimetofailurePDF(PDFParser.parseRealDistributionToString(errorMode.getTimetofailurePDF()));
                outputErrorMode.setOutgoingFailure(errorMode.getOutgoingFailure().getDescription());
                List<FaultModeDto> outputFaultModes = new ArrayList<>();
                for (FaultMode faultMode : errorMode.getInputFaultModes()) {
                    FaultModeDto outputFaultMode = new FaultModeDto();
                    outputFaultMode.setName(faultMode.getName());
                    if (faultMode.getClass().equals(EndogenousFaultMode.class)) {
                        outputFaultMode.setArisingPDF(((EndogenousFaultMode) faultMode).getArisingPDFToString());
                        outputFaultMode.setFaultType(FaultType.ENDOGENOUS);
                    } else
                        outputFaultMode.setFaultType(FaultType.EXOGENOUS);
                    outputFaultModes.add(outputFaultMode);
                }
                outputErrorMode.setInputFaultModes(outputFaultModes);
                outputErrorModes.add(outputErrorMode);
            }
            for (PropagationPort propagationPort : metaComponent.getPropagationPorts()) {
                PropagationPortDto outputPropagationPort = new PropagationPortDto();
                outputPropagationPort.setAffectedComponent(propagationPort.getAffectedComponent().getName());
                outputPropagationPort.setExogenousFaultMode(propagationPort.getExogenousFaultMode().getName());
                outputPropagationPort.setPropagatedFailureMode(propagationPort.getPropagatedFailureMode().getDescription());
                outputPropagationPorts.add(outputPropagationPort);
            }
            if (metaComponent.getCompositionPort() != null) {
                CompositionPortDto outputCompositionPort = new CompositionPortDto();
                outputCompositionPort.setParent(metaComponent.getCompositionPort().getParent().getName());
                List<String> children = metaComponent.getCompositionPort().getChildren().stream().map(MetaComponent::getName).collect(Collectors.toList());
                outputCompositionPort.setChildren(children);
                outputMetaComponent.setCompositionPort(outputCompositionPort);
            }
            outputMetaComponent.setErrorModes(outputErrorModes);
            outputMetaComponent.setPropagationPorts(outputPropagationPorts);
            outputMetaComponents.add(outputMetaComponent);
        }
        outputSystemDto.setComponents(outputMetaComponents);
        return outputSystemDto;
    }

    private static String generateFailuresFaultName(InputNodeDto failure, String gate) {

        if (!failure.getAliases().isEmpty()) {
            for (AliasDto alias : failure.getAliases()) {
                if (alias.getGate().equalsIgnoreCase(gate))
                    return alias.getFaultName();
            }
        }
        return failure.getExternalId().replace("Failure", "Fault");
    }

}
