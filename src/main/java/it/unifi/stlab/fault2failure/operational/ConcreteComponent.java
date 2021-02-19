package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;

import java.util.ArrayList;
import java.util.List;

public class ConcreteComponent {
    private final String serial;
    private final List<Fault> faultList;
    private Component componentType;

    /**
     * Create a Component by giving its serial number (which must be unique) and its component type.
     *
     * @param serial        a string that describes the serial number of the component.
     * @param componentType a reference to MetaComponent that expresses the object's Component type.
     */
    public ConcreteComponent(String serial, it.unifi.stlab.fault2failure.knowledge.composition.Component componentType) {
        this.serial = serial;
        this.componentType = componentType;
        faultList = new ArrayList<>();
    }

    public String getSerial() {
        return this.serial;
    }

    public it.unifi.stlab.fault2failure.knowledge.composition.Component getComponentType() {
        return this.componentType;
    }

    public void setComponentType(it.unifi.stlab.fault2failure.knowledge.composition.Component componentType) {
        this.componentType = componentType;
    }

    public List<Fault> getFaultList() {
        return faultList;
    }

    public void addFailure(Fault fault) {
        this.faultList.add(fault);
    }

    public void removeFailure(Fault fault) {
        if (faultList.contains(fault))
            this.faultList.remove(fault);
        else
            throw new IllegalArgumentException("No such failure inside the list");
    }

    /**
     * Check if the component is already Failed with the same Failure.
     * This means that if, for example, the Component A has already had a Failure of type Electric Defect, can't
     * fail again with the same failure, but can, instead, have another Failure of type Mechanic Defect.
     *
     * @param failDescription the description of the Failure. It must be unique.
     * @return a boolean value that's true if the component is already failed, else it's false.
     */
    public boolean isFailureAlreadyOccurred(String failDescription) {
        return faultList.stream().anyMatch(failure -> failure.getDescription().equals(failDescription));
    }
}
