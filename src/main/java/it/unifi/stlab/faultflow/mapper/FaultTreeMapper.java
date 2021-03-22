package it.unifi.stlab.faultflow.mapper;

import it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree.*;
import it.unifi.stlab.faultflow.dto.petrinet.*;
import it.unifi.stlab.faultflow.dto.system.*;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;
import it.unifi.stlab.faultflow.model.utils.PDFParser;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FaultTreeMapper {
    private static boolean isComponentInSystem(System system, String name) {
        return system.getComponents().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findAny().isPresent();
    }

    private static Component getComponentInSystem(System system, String name) {
        Optional<Component> mc = system.getComponents().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findAny();
        if (mc.isPresent())
            return mc.get();
        else {
            Component component = new Component(name);
            system.addComponent(component);
            return component;
        }
    }

    public static void decorateSystem(InputFaultTreeDto inputFaultTreeDto, System system) {
        Queue<InputNodeDto> nodeToVisit = new LinkedList<>();
        HashMap<String, FaultMode> faultModes = new HashMap<>();
        HashMap<String, FailureMode> failureModes = new HashMap<>();
        for (String topEvent : inputFaultTreeDto.getTopEvents()) {
            nodeToVisit.add(getNodeFromID(inputFaultTreeDto, topEvent));
        }
        while (!nodeToVisit.isEmpty()) {
            InputNodeDto parent = nodeToVisit.remove();
            List<InputParentingDto> parentings = getChildrenFromNodeID(inputFaultTreeDto, parent.getExternalId());
            for (InputParentingDto parenting : parentings) {
                InputNodeDto currentNode = getNodeFromID(inputFaultTreeDto, parenting.getChildId());

                if (currentNode.getNodeType() == NodeType.GATE) {
                    Component mc = getComponentInSystem(system, currentNode.getComponentId());
                    if (!mc.isErrorModeNamePresent(currentNode.getLabel())) {
                        ErrorMode errorMode = new ErrorMode(currentNode.getLabel());
                        FailureMode fm = failureModes.get(parent.getLabel());
                        if (fm != null)
                            errorMode.setOutGoingFailure(fm);
                        else {
                            errorMode.setOutGoingFailure(new FailureMode(parent.getLabel()));
                            failureModes.put(errorMode.getOutgoingFailure().getDescription(), errorMode.getOutgoingFailure());
                        }
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
        System system=null;
        for(String topEvent : inputFaultTreeDto.getTopEvents()){
            InputNodeDto rootNode = getNodeFromID(inputFaultTreeDto, topEvent);
            Component topComponent = new Component(rootNode.getComponentId());
            system = new System(rootNode.getComponentId() + "_SYS");
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

    public static InputNodeDto getNodeFromLabel(InputFaultTreeDto faultTree, String label) {
        return faultTree.getNodes().stream().filter(x -> x.getLabel().equalsIgnoreCase(label)).findFirst().get();
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
                            FailureMode failureMode = new FailureMode(child.getLabel());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String[] faultNameAndProbability = getFailuresFaultNameAndProbability(child, nodeDto.getComponentId());
                            String faultname = faultNameAndProbability[0];
                            BigDecimal routingProbability = BigDecimal.valueOf(Double.parseDouble(faultNameAndProbability[1]));
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            be.append(faultname).append("||");
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            Component mc = getComponentInSystem(system, child.getComponentId());
                            Component affectedComponent = getComponentInSystem(system, nodeDto.getComponentId());
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, affectedComponent, routingProbability));
                            break;
                        default:
                            be.append(child.getLabel()).append("||");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getLabel());
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
                            FailureMode failureMode = new FailureMode(child.getLabel());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String[] faultNameAndProbability = getFailuresFaultNameAndProbability(child, nodeDto.getComponentId());
                            String faultname = faultNameAndProbability[0];
                            BigDecimal routingProbability = BigDecimal.valueOf(Double.parseDouble(faultNameAndProbability[1]));
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            be.append(faultname).append("&&");
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            Component mc = getComponentInSystem(system, child.getComponentId());
                            Component affectedComponent = getComponentInSystem(system, nodeDto.getComponentId());
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, affectedComponent, routingProbability));
                            break;
                        default:
                            be.append(child.getLabel()).append("&&");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getLabel());
                            endogenousFaultMode.setArisingPDF(child.getPdf());
                            errorMode.addInputFaultMode(endogenousFaultMode);
                            faultModes.put(child.getLabel(), endogenousFaultMode);
                    }
                }
                be.delete(be.length() - 2, be.length());
                break;
            case KOUTOFN:
                be.append(nodeDto.getK()).append("/").append(nodeDto.getN()).append("(");
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    switch (child.getNodeType()) {
                        case FAILURE:
                            FailureMode failureMode = new FailureMode(child.getLabel());
                            failureModes.put(failureMode.getDescription(), failureMode);
                            String[] faultNameAndProbability = getFailuresFaultNameAndProbability(child, nodeDto.getComponentId());
                            String faultname = faultNameAndProbability[0];
                            BigDecimal routingProbability = BigDecimal.valueOf(Double.parseDouble(faultNameAndProbability[1]));
                            be.append(faultname).append(",");
                            ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(faultname);
                            errorMode.addInputFaultMode(exogenousFaultMode);
                            faultModes.put(faultname, exogenousFaultMode);
                            if (!nodeToVisit.contains(child))
                                nodeToVisit.add(child);
                            Component mc = getComponentInSystem(system, child.getComponentId());
                            Component affectedComponent = getComponentInSystem(system, nodeDto.getComponentId());
                            mc.addPropagationPort(new PropagationPort(failureMode, exogenousFaultMode, affectedComponent, routingProbability));
                            break;
                        default:
                            be.append(child.getLabel()).append(",");
                            EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(child.getLabel());
                            endogenousFaultMode.setArisingPDF(child.getPdf());
                            errorMode.addInputFaultMode(endogenousFaultMode);
                            faultModes.put(child.getLabel(), endogenousFaultMode);
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
        for (Component component : system.getComponents()) {
            MetaComponentDto outputMetaComponent = new MetaComponentDto();
            outputMetaComponent.setName(component.getName());
            List<CompositionPortDto> outputCompositionPorts = new ArrayList<>();
            List<ErrorModeDto> outputErrorModes = new ArrayList<>();
            List<PropagationPortDto> outputPropagationPorts = new ArrayList<>();
            for (ErrorMode errorMode : component.getErrorModes()) {
                ErrorModeDto outputErrorMode = new ErrorModeDto();
                outputErrorMode.setName(errorMode.getName());
                outputErrorMode.setActivationFunction(errorMode.getActivationFunction().toString());
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
            for (PropagationPort propagationPort : component.getPropagationPorts()) {
                PropagationPortDto outputPropagationPort = new PropagationPortDto();
                outputPropagationPort.setAffectedComponent(propagationPort.getAffectedComponent().getName());
                outputPropagationPort.setExogenousFaultMode(propagationPort.getExogenousFaultMode().getName());
                outputPropagationPort.setPropagatedFailureMode(propagationPort.getPropagatedFailureMode().getDescription());
                if(propagationPort.getRoutingProbability() != BigDecimal.ONE)
                    outputPropagationPort.setRoutingProbability(propagationPort.getRoutingProbability().doubleValue());
                outputPropagationPorts.add(outputPropagationPort);
            }
            for (CompositionPort compositionPort : component.getCompositionPorts()) {
                CompositionPortDto outputCompositionPort = new CompositionPortDto();
                outputCompositionPort.setParent(compositionPort.getParent().getName());
                outputCompositionPort.setChild(compositionPort.getChild().getName());
                outputCompositionPorts.add(outputCompositionPort);
            }
            outputMetaComponent.setErrorModes(outputErrorModes);
            outputMetaComponent.setPropagationPorts(outputPropagationPorts);
            outputMetaComponent.setCompositionPorts(outputCompositionPorts);
            outputMetaComponents.add(outputMetaComponent);
        }
        outputSystemDto.setComponents(outputMetaComponents);
        return outputSystemDto;
    }

    private static String[] getFailuresFaultNameAndProbability(InputNodeDto failure, String gateComponentID) {
        //first element is the fault's name, second element is fault routing probability
        String[] res = new String[2];
        if (failure.getActsAs()!=null) {
            for (AliasDto alias : failure.getActsAs()) {
                if (alias.getComponentId().equalsIgnoreCase(gateComponentID)) {
                    res[0] = alias.getFaultName();
                    if(alias.getRoutingProbability() != null) {
                        if (alias.getRoutingProbability() < 1) {
                            res[1] = "" + alias.getRoutingProbability();
                            return res;
                        }

                    }
                    else {
                        res[1] = "1";
                        return res;
                    }
                }
            }
        }
        else {
            res[0] = failure.getLabel().replace("Failure", "Fault");
            res[1] = "1";
            return res;
        }

        throw new NullPointerException("There's no ActAs in"+ failure.getLabel()+" with the same ComponentID as the Gate's: "+ gateComponentID);
    }

}
