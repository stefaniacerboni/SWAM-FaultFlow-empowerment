package it.unifi.stlab.faultflow.model.operational;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSystem {
    String serialNumber;
    System system;
    ConcreteComponent topConcreteComponent;
    List<ConcreteComponent> concreteComponentList;

    public ConcreteSystem() {
        serialNumber ="";
        system = null;
        topConcreteComponent = null;
        concreteComponentList = new ArrayList<>();
    }

    public ConcreteSystem(String serialNumber, System system, ConcreteComponent topConcreteComponent, List<ConcreteComponent> concreteComponentList) {
        this.serialNumber = serialNumber;
        this.system = system;
        this.topConcreteComponent = topConcreteComponent;
        this.concreteComponentList = concreteComponentList;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public ConcreteComponent getTopConcreteComponent() {
        return topConcreteComponent;
    }

    public void setTopConcreteComponent(ConcreteComponent topConcreteComponent) {
        this.topConcreteComponent = topConcreteComponent;
    }

    public List<ConcreteComponent> getConcreteComponentList() {
        return concreteComponentList;
    }

    public void setConcreteComponentList(List<ConcreteComponent> concreteComponentList) {
        this.concreteComponentList = concreteComponentList;
    }
}
