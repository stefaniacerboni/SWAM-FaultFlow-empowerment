package it.unifi.stlab.faultflow.model.knowledge.composition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class System {
    private String name;
    private String manufacturer;
    private String model;
    private List<Component> components;
    private Component topLevelComponent;

    public System(){
        this.name="";
        this.components = new ArrayList<>();
    }

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

    public void setName(String name){
        this.name=name;
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Component getTopLevelComponent() {
        return topLevelComponent;
    }

    public void setTopLevelComponent(Component component) throws IllegalArgumentException {
        //assure that top level component it's inside the list of components that make up the system
        if (components.contains(component))
            this.topLevelComponent = component;
        else
            throw new IllegalArgumentException("Top level component must be inside the system");
    }

    public void addComponent(Component... c) {
        this.components.addAll(Arrays.asList(c));
    }

    public Component getComponent(String componentID){
        return components.stream().filter(x-> x.getName().equals(componentID)).findFirst().get();
    }
}
