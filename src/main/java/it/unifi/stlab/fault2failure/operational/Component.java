package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;

import java.util.ArrayList;
import java.util.List;

public class Component {
    private final String serial;
    private MetaComponent componentType;
    private List<Failure> failureList;

    /**
     * Create a Component by giving its serial number (which must be unique) and its component type.
     * @param serial a string that describes the serial number of the component.
     * @param componentType a reference to MetaComponent that expresses the object's Component type.
     */
    public Component(String serial, MetaComponent componentType){
        this.serial=serial;
        this.componentType=componentType;
        failureList = new ArrayList<>();
    }
    public String getSerial(){
        return this.serial;
    }
    public MetaComponent getComponentType(){
        return this.componentType;
    }
    public void setComponentType(MetaComponent componentType){
        this.componentType=componentType;
    }
    public List<Failure> getFailureList() {
        return failureList;
    }
    public void addFailure(Failure failure){
        this.failureList.add(failure);
    }
    public void removeFailure(Failure failure){
        if(failureList.contains(failure))
            this.failureList.remove(failure);
        else
            throw new IllegalArgumentException("No such failure inside the list");
    }

    /**
     * Check if the component is already Failed with the same Failure.
     * This means that if, for example, the Component A has already had a Failure of type Electric Defect, can't
     * fail again with the same failure, but can, instead, have another Failure of type Mechanic Defect.
     * @param failDescription the description of the Failure. It must be unique.
     * @return a boolean value that's true if the component is already failed, else it's false.
     */
    public boolean isFailureAlreadyOccurred(String failDescription){
        return failureList.stream().anyMatch(failure -> failure.getDescription().equals(failDescription));
    }
}
