package it.unifi.stlab.mapper;

import it.unifi.stlab.DTO.BDD.InputBddDto;
import it.unifi.stlab.DTO.BDD.InputParentingDto;
import it.unifi.stlab.DTO.system.CompositionPortDto;
import it.unifi.stlab.DTO.system.MetaComponentDto;
import it.unifi.stlab.DTO.system.OutputSystemDto;
import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemMapper {
    public static System BddToSystem(InputBddDto inputBddDto){
        System bddSystem = new System(inputBddDto.getRootId()+"_System");
        MetaComponent topLevelComponent = new MetaComponent(inputBddDto.getRootId());
        bddSystem.addComponent(topLevelComponent);
        bddSystem.setTopLevelComponent(topLevelComponent);
        for(InputParentingDto parenting: inputBddDto.getParentings()){
            MetaComponent metaComponent = new MetaComponent(parenting.getLabel());
            bddSystem.addComponent(metaComponent);
        }
        Map<String, List<InputParentingDto>> orderByParent = inputBddDto.getParentings().stream()
                .collect(Collectors.groupingBy(InputParentingDto::getParentId));
        for(Map.Entry<String, List<InputParentingDto>> entry : orderByParent.entrySet()){
            MetaComponent mainComponent = bddSystem.getComponent(entry.getKey());
            CompositionPort compositionPort = new CompositionPort(mainComponent);
            for(InputParentingDto inputParentingDto: entry.getValue()){
                compositionPort.addChild(bddSystem.getComponent(inputParentingDto.getLabel()));
            }
        }
        return bddSystem;
    }
    public static OutputSystemDto systemToOutputSystem(System system){
        OutputSystemDto outputSystemDto = new OutputSystemDto();
        outputSystemDto.setName(system.getName());
        List<MetaComponentDto> componentDtos = new ArrayList<>();
        for(MetaComponent metaComponent: system.getComponents()){
            MetaComponentDto metaComponentDto = new MetaComponentDto();
            metaComponentDto.setName(metaComponent.getName());
            CompositionPortDto compositionPortDto = new CompositionPortDto();
            compositionPortDto.setParent(metaComponent.getCompositionPort().getParent().getName());
            List<String> children = new ArrayList<>();
            for(MetaComponent mc: metaComponent.getCompositionPort().getChildren()) {
                children.add(mc.getName());
            }
            compositionPortDto.setChildren(children);
            metaComponentDto.setCompositionPort(compositionPortDto);
            componentDtos.add(metaComponentDto);
        }
        outputSystemDto.setComponents(componentDtos);
        return outputSystemDto;
    }
}
