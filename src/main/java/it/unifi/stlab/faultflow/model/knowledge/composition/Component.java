package it.unifi.stlab.faultflow.model.knowledge.composition;

import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Component {
    private final String name;
    private List<CompositionPort> children;
    private final List<ErrorMode> errorModes;
    private final List<PropagationPort> propagationPorts;

    /**
     * Create a MetaComponent by setting its name to the String passed in input and its CompositionPort to null
     *
     * @param name It's the name of the ComponentType we want to describe through a MetaComponent.
     */
    public Component(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.errorModes = new ArrayList<>();
        this.propagationPorts = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<CompositionPort> getCompositionPorts() {
        return this.children;
    }

    /**
     * Set the CompositionPort in which the MetaComponent appears as the Parent or as one of the children.
     * This method is always called from CompositionPort class. Thus it is package-protected.
     *
     * @param cp This MetaComponent's CompositionPort.
     */
    protected void setCompositionPorts(List<CompositionPort> cp) {
        this.children = cp;
    }

    public List<ErrorMode> getErrorModes() {
        return errorModes;
    }

    public List<PropagationPort> getPropagationPorts() {
        return propagationPorts;
    }

    public void addErrorMode(ErrorMode... errorMode) {
        this.errorModes.addAll(Arrays.asList(errorMode));
    }

    public void addPropagationPort(PropagationPort... propagationPort) {
        this.propagationPorts.addAll(Arrays.asList(propagationPort));
    }

    public void addPropagationPorts(List<PropagationPort> propagationPorts) {
        this.propagationPorts.addAll(propagationPorts);
    }

    public boolean isErrorModeNamePresent(String errorModeName){
        return errorModes.stream().filter(x-> x.getName().equalsIgnoreCase(errorModeName)).findAny().isPresent();
    }

    public void addCompositionPorts(CompositionPort... compositionPort){
        this.children.addAll(Arrays.asList(compositionPort));
    }

}
