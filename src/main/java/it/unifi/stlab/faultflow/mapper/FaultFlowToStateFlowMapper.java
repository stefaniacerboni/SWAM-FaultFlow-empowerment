package it.unifi.stlab.faultflow.mapper;
import io.jsonwebtoken.lang.Collections;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;
import it.unifi.stlab.stateflow.*;
import org.apache.commons.math3.distribution.ConstantRealDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FaultFlowToStateFlowMapper {

    public StateFlowModel map(System faultFlowSystem) {
        Map<String, Node> nodes = new HashMap<>();
        Map<String, StateTransition> transitions = new HashMap<>();
        Map<String, String> transitionBooleanExpression = new HashMap<>();
        for(Component component: faultFlowSystem.getComponents()){
            for(ErrorMode errorMode: component.getErrorModes()){
                List<String> inputNodes = new ArrayList<>();
                for(FaultMode faultMode: errorMode.getInputFaultModes()){
                    if(faultMode.getClass() == EndogenousFaultMode.class){
                        if (nodes.get(faultMode.getName()) == null) {
                            Node a = new Node(faultMode.getName()+"_Occurrence", 1);
                            a.setState("active", true);
                            nodes.put(a.getId(), a);
                            List<String> input = new ArrayList<>();
                            input.add(a.getId());
                            Node b = new Node(faultMode.getName(), 0);
                            b.setState("active", false);
                            nodes.put(b.getId(), b);
                            input.add(a.getId());
                            StateTransition aTOb_Transition = new StateTransition(b.getId()+ "_Transition", input, b.getId(), ((EndogenousFaultMode) faultMode).getArisingPDF(), null);
                            transitions.put(aTOb_Transition.getId(), aTOb_Transition);
                            inputNodes.add(b.getId());
                        }
                    }
                    else {
                        if (nodes.get(faultMode.getName()) == null) {
                            Node node = new Node(faultMode.getName(), 0);
                            node.setState("active", false);
                            nodes.put(node.getId(), node);
                            inputNodes.add(node.getId());
                        }
                    }
                }
                if(nodes.get(errorMode.getOutgoingFailure().getDescription()) == null) {
                    Node failure_node = new Node(errorMode.getOutgoingFailure().getDescription(), 0);
                    failure_node.setState("active", false);
                    nodes.put(failure_node.getId(), failure_node);
                }
                if(transitions.get(errorMode.getName()) == null) {
                    StateTransition em_Transition = new StateTransition(errorMode.getName(), inputNodes, errorMode.getOutgoingFailure().getDescription(), null, errorMode.getTimetofailurePDF());
                    transitions.put(em_Transition.getId(), em_Transition);
                    transitionBooleanExpression.put(em_Transition.getId(), errorMode.getActivationFunction().toSimpleString());
                    transitions.put(em_Transition.getId(), em_Transition);
                }
            }
            for(PropagationPort propagationPort: component.getPropagationPorts()){
                Node failure_node;
                if(nodes.get(propagationPort.getPropagatedFailureMode().getDescription())==null){
                    failure_node = new Node(propagationPort.getPropagatedFailureMode().getDescription(), 0);
                    failure_node.setState("active", false);
                    nodes.put(failure_node.getId(), failure_node);
                }
                failure_node = nodes.get(propagationPort.getPropagatedFailureMode().getDescription());
                Node exofault_node;
                if(nodes.get(propagationPort.getExogenousFaultMode().getName())==null){
                    exofault_node = new Node(propagationPort.getExogenousFaultMode().getName(), 0);
                    exofault_node.setState("active", false);
                    nodes.put(exofault_node.getId(), exofault_node);
                }
                exofault_node = nodes.get(propagationPort.getExogenousFaultMode().getName());
                List<String> input = new ArrayList<>();
                input.add(failure_node.getId());
                RealDistribution diracDeltaAtZero = new ConstantRealDistribution(0.0);
                StateTransition fmTOexo_Transition = new StateTransition(failure_node.getId()+ "_TO_"+exofault_node.getId(), input, exofault_node.getId(), diracDeltaAtZero, diracDeltaAtZero);
                transitions.put(fmTOexo_Transition.getId(), fmTOexo_Transition);
            }
        }
        for(String transition: transitions.keySet()){
            Predicate<List<Node>> activationFunction = createActivationFunction(transitionBooleanExpression.get(transition), nodes);
            transitions.get(transition).setCondition(activationFunction);
        }
        return new StateFlowModel(nodes, transitions);
    }

    private Predicate<List<Node>> createActivationFunction(String condition, Map<String, Node> nodes) {
        return nodeList -> {
            BooleanExpressionParser parser = new BooleanExpressionParser(nodes);
            return parser.evaluate(condition);
        };
    }
}
