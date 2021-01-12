package it.unifi.stlab.mapper;

import it.unifi.stlab.dto.inputsystemdto.bdd.InputBddDto;
import it.unifi.stlab.dto.inputsystemdto.bdd.InputParentingDto;
import it.unifi.stlab.dto.system.CompositionPortDto;
import it.unifi.stlab.dto.system.MetaComponentDto;
import it.unifi.stlab.dto.system.OutputSystemDto;
import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemMapper {
    public static System BddToSystem(InputBddDto inputBddDto){
        System bddSystem = new System(inputBddDto.getRootId()+"_System");
        Component topLevelComponent = new Component(inputBddDto.getRootId());
        bddSystem.addComponent(topLevelComponent);
        bddSystem.setTopLevelComponent(topLevelComponent);
        for(InputParentingDto parenting: inputBddDto.getParentings()){
            Component component = new Component(parenting.getLabel());
            bddSystem.addComponent(component);
        }
        Map<String, List<InputParentingDto>> orderByParent = inputBddDto.getParentings().stream()
                .collect(Collectors.groupingBy(InputParentingDto::getParentId));
        for(Map.Entry<String, List<InputParentingDto>> entry : orderByParent.entrySet()){
            Component mainComponent = bddSystem.getComponent(entry.getKey());
            for(InputParentingDto inputParentingDto: entry.getValue()){
                CompositionPort compositionPort = new CompositionPort(bddSystem.getComponent(inputParentingDto.getLabel()),
                        mainComponent);
                mainComponent.addCompositionPorts(compositionPort);
            }
        }
        return bddSystem;
    }
    public static OutputSystemDto systemToOutputSystem(System system){
        OutputSystemDto outputSystemDto = new OutputSystemDto();
        outputSystemDto.setName(system.getName());
        List<MetaComponentDto> componentDtos = new ArrayList<>();
        for(Component component : system.getComponents()){
            MetaComponentDto metaComponentDto = new MetaComponentDto();
            metaComponentDto.setName(component.getName());
            List<CompositionPortDto> compositionPortDtos = new ArrayList<>();
            for(CompositionPort compositionPort: component.getCompositionPorts()){
                CompositionPortDto compositionPortDto = new CompositionPortDto();
                compositionPortDto.setParent(compositionPort.getParent().getName());
                compositionPortDto.setChild(compositionPort.getChild().getName());
                compositionPortDtos.add(compositionPortDto);
            }
            metaComponentDto.setCompositionPort(compositionPortDtos);
            componentDtos.add(metaComponentDto);
        }
        outputSystemDto.setComponents(componentDtos);
        return outputSystemDto;
    }
}
