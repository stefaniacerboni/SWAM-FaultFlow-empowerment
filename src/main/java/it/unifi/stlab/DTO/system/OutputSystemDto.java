package it.unifi.stlab.DTO.system;


import java.util.List;

public class OutputSystemDto {
    private String name;
    private String manufacturer;
    private String model;
    private List<MetaComponentDto> components;
    private String topLevelComponent;
    private List<CompositionPortDto> compositionPorts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MetaComponentDto> getComponents() {
        return components;
    }

    public void setComponents(List<MetaComponentDto> components) {
        this.components = components;
    }

    public String getTopLevelComponent() {
        return topLevelComponent;
    }

    public void setTopLevelComponent(String topLevelComponent) {
        this.topLevelComponent = topLevelComponent;
    }

    public List<CompositionPortDto> getCompositionPorts() {
        return compositionPorts;
    }

    public void setCompositionPorts(List<CompositionPortDto> compositionPorts) {
        this.compositionPorts = compositionPorts;
    }
}