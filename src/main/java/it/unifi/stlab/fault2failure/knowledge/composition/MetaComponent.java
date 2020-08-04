package it.unifi.stlab.fault2failure.knowledge.composition;

import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetaComponent {
    private String name;
    private CompositionPort compositionPort;
    private List<ErrorMode> errorModes;
    private List<PropagationPort> propagationPort;

    //  aggiungere liste errorModes (di ErrorMode) e propagations (di PropagationPort)

    /**
     * Create a MetaComponent by setting its name to the String passed in input and its CompositionPort to null
     * @param name It's the name of the ComponentType we want to describe through a MetaComponent.
     */
    public MetaComponent(String name){
        this.name = name;
        this.compositionPort=null;
        this.errorModes= new ArrayList<>();
        this.propagationPort=new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }
    public CompositionPort getCompositionPort(){return this.compositionPort;}

    /**
     * Set the CompositionPort in which the MetaComponent appears as the Parent or as one of the children.
     * This method is always called from CompositionPort class. Thus it is package-protected.
     * @param cp This MetaComponent's CompositionPort.
     */
    protected void setCompositionPort(CompositionPort cp){
        this.compositionPort = cp;
    }

    public void addErrorMode(ErrorMode... errorMode){
        this.errorModes.addAll(Arrays.asList(errorMode));
    }
    public void addPropagationPort(PropagationPort... propagationPort){
        this.propagationPort.addAll(Arrays.asList(propagationPort));
    }
    public void addPropagationPort(List<PropagationPort> propagationPorts){
        this.propagationPort.addAll(propagationPorts);
    }

    public List<ErrorMode> getErrorModes() {
        return errorModes;
    }

    public List<PropagationPort> getPropagationPort() {
        return propagationPort;
    }
}
