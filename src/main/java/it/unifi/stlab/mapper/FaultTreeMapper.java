package it.unifi.stlab.mapper;

import it.unifi.stlab.DTO.faultTree.InputFaultTreeDto;
import it.unifi.stlab.DTO.faultTree.InputNodeDto;
import it.unifi.stlab.DTO.faultTree.InputParentingDto;
import it.unifi.stlab.DTO.faultTree.NodeType;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FaultTreeMapper {
    public System generateSystemFromFaultTree(InputFaultTreeDto inputFaultTreeDto) {
        System sys = new System(inputFaultTreeDto.getRootId());
        String[] nodeNameSplitted;
        for (InputNodeDto node : inputFaultTreeDto.getNodes()) {
            nodeNameSplitted = node.getLabel().split("_");
            MetaComponent mc;
            if (!isComponentInSystem(sys, nodeNameSplitted[0])) {
                mc = new MetaComponent(nodeNameSplitted[0]);
            } else {
                mc = getComponentInSystem(sys, nodeNameSplitted[0]);
            }
            if (node.getNodeType() == NodeType.GATE) {
                //vai a vedere nei parentings quali sono i suoi child e mettili come fault in ingresso
                //vai a vedere anche qual è il suo parent (auspicabilmente un failure, se non è un failure signfica che è un gate composto)

            }
        }
        return sys;
    }

    private static  boolean isComponentInSystem(System system, String name) {
        return system.getComponents().stream().filter(x -> x.getName().equals(name)).findAny().isPresent();
    }

    private static MetaComponent getComponentInSystem(System system, String name) {
        return system.getComponents().stream().filter(x -> x.getName().equals(name)).findAny().get();
    }

    public static System generateSystemFromFaultTree2(InputFaultTreeDto inputFaultTreeDto) {
        System sys = new System(inputFaultTreeDto.getRootId() + "_SYS");
        String[] nodeNameSplitted;
        nodeNameSplitted = inputFaultTreeDto.getRootId().split("_");
        MetaComponent topComponent = new MetaComponent(nodeNameSplitted[0]);
        sys.addComponent(topComponent);
        sys.setTopLevelComponent(topComponent);
        InputParentingDto lastgate = getChildrenFromNodeID(inputFaultTreeDto, inputFaultTreeDto.getRootId()).get(0);
        ErrorMode errorMode = new ErrorMode(lastgate.getChildId());
        errorMode.setOutGoingFailure(new FailureMode(inputFaultTreeDto.getRootId()));
        HashMap<String, FaultMode> faulmodes = new HashMap<>();
        errorMode.setEnablingCondition(getEnablingFunctionByNavigatingTree(inputFaultTreeDto, lastgate.getChildId(), errorMode), faulmodes);

        for (InputNodeDto node : inputFaultTreeDto.getNodes()) {
            nodeNameSplitted = node.getLabel().split("_");
            MetaComponent mc;
            if (!isComponentInSystem(sys, nodeNameSplitted[0])) {
                mc = new MetaComponent(nodeNameSplitted[0]);
            } else {
                mc = getComponentInSystem(sys, nodeNameSplitted[0]);
            }
            if (node.getNodeType() == NodeType.GATE) {
                //vai a vedere nei parentings quali sono i suoi child e mettili come fault in ingresso
                //vai a vedere anche qual è il suo parent (auspicabilmente un failure, se non è un failure signfica che è un gate composto)

            }
        }
        return sys;
    }

    public static  List<InputParentingDto> getChildrenFromNodeID(InputFaultTreeDto faultTree, String nodeID) {
        return faultTree.getParentings().stream().filter(x -> x.getParentId().equals(nodeID)).collect(Collectors.toList());
    }

    public static InputNodeDto getNodeFromID(InputFaultTreeDto faultTree, String nodeID) {
        return faultTree.getNodes().stream().filter(x -> x.getExternalId().equalsIgnoreCase(nodeID)).findFirst().get();
    }

    public static String getEnablingFunctionByNavigatingTree(InputFaultTreeDto faultTreeDto, String currentNode, ErrorMode errorMode) {
        StringBuilder be = new StringBuilder();
        InputNodeDto nodeDto = getNodeFromID(faultTreeDto, currentNode);
        switch (nodeDto.getGateType()) {
            case OR:
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    switch (child.getNodeType()) {
                        case GATE:
                            be.append(getEnablingFunctionByNavigatingTree(faultTreeDto, child.getExternalId(), errorMode));
                        case FAILURE:
                            String faultname = child.getExternalId().replace("Failure", "Fault");
                            be.append(faultname).append("||");
                        default:
                            be.append(child.getExternalId()).append("||");
                    }
                }
                be.substring(0, be.length() - 2);
            case AND:
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    switch (child.getNodeType()) {
                        case GATE:
                            be.append(getEnablingFunctionByNavigatingTree(faultTreeDto, child.getExternalId(), errorMode));
                        case FAILURE:
                            String faultname = child.getExternalId().replace("Failure", "Fault");
                            be.append(faultname).append("&&");
                        default:
                            be.append(child.getExternalId()).append("&&");
                    }
                }
                be.substring(0, be.length() - 2);
            case KOUTOFN:
                be.append(nodeDto.getK()).append("/").append(nodeDto.getN());
                for (InputParentingDto parentingDto : getChildrenFromNodeID(faultTreeDto, nodeDto.getExternalId())) {
                    InputNodeDto child = getNodeFromID(faultTreeDto, parentingDto.getChildId());
                    //hope they're all basic or failures
                    switch (child.getNodeType()) {
                        case FAILURE:
                            String faultname = child.getExternalId().replace("Failure", "Fault");
                            be.append(faultname).append(",");
                        default:
                            be.append(child.getExternalId()).append(",");
                    }
                }
                be.substring(0, be.length() - 1);
            default:
        }

        return be.toString();
    }


}
