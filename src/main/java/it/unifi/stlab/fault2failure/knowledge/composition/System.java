package it.unifi.stlab.fault2failure.knowledge.composition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class System {
    private final String name;
    private String manufacturer;
    private String model;
    private List<MetaComponent> components;
    private MetaComponent topLevelComponent;

    public System(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public System(String name, String manufacturer, String model) {
        this(name);
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public String getName() {
        return name;
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

    public List<MetaComponent> getComponents() {
        return components;
    }

    public void setComponents(List<MetaComponent> components) {
        this.components = components;
    }

    public MetaComponent getTopLevelComponent() {
        return topLevelComponent;
    }

    public void setTopLevelComponent(MetaComponent metaComponent) throws IllegalArgumentException {
        //assure that top level component it's inside the list of components that make up the system
        if (components.contains(metaComponent))
            this.topLevelComponent = metaComponent;
        else
            throw new IllegalArgumentException("Top level component must be inside the system");
    }

    public void addComponent(MetaComponent... c) {
        this.components.addAll(Arrays.asList(c));
    }

    public MetaComponent getComponent(String componentID){
        return components.stream().filter(x-> x.getName().equals(componentID)).findFirst().get();
    }
}
