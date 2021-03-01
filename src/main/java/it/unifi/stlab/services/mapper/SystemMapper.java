package it.unifi.stlab.services.mapper;

import it.unifi.stlab.dto.inputsystemdto.bdd.InputBddDto;
import it.unifi.stlab.dto.inputsystemdto.bdd.InputBlockDto;
import it.unifi.stlab.dto.inputsystemdto.bdd.InputParentingDto;
import it.unifi.stlab.dto.system.CompositionPortDto;
import it.unifi.stlab.dto.system.MetaComponentDto;
import it.unifi.stlab.dto.system.OutputSystemDto;
import it.unifi.stlab.model.knowledge.composition.Component;
import it.unifi.stlab.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.model.knowledge.composition.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemMapper {
    public static System BddToSystem(InputBddDto inputBddDto){
        System bddSystem = new System();
        Map<String, String> idToNames = new HashMap<>();
        for(InputBlockDto block: inputBddDto.getBlocks()){
            Component component = new Component(block.getName());
            bddSystem.addComponent(component);
            idToNames.put(block.getExternalId(), block.getName());
        }
        Component topLevelComponent = bddSystem.getComponent(idToNames.get(inputBddDto.getRootId()));
        bddSystem.setTopLevelComponent(topLevelComponent);
        bddSystem.setName(topLevelComponent.getName()+"_System");
        Map<String, List<InputParentingDto>> orderByParent = inputBddDto.getParentings().stream()
                .collect(Collectors.groupingBy(InputParentingDto::getParentId));
        for(Map.Entry<String, List<InputParentingDto>> entry : orderByParent.entrySet()){
            Component mainComponent = bddSystem.getComponent(idToNames.get(entry.getKey()));
            for(InputParentingDto inputParentingDto: entry.getValue()){
                Component child = bddSystem.getComponent(idToNames.get(inputParentingDto.getChildId()));
                CompositionPort compositionPort = new CompositionPort(child, mainComponent);
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
            metaComponentDto.setCompositionPorts(compositionPortDtos);
            componentDtos.add(metaComponentDto);
        }
        outputSystemDto.setComponents(componentDtos);
        return outputSystemDto;
    }
}
