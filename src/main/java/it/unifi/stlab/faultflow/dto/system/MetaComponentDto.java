package it.unifi.stlab.faultflow.dto.system;

import java.util.List;

public class MetaComponentDto {
    private String name;
    private List<CompositionPortDto> compositionPorts;
    private List<ErrorModeDto> errorModes;
    private List<PropagationPortDto> propagationPorts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CompositionPortDto> getCompositionPorts() {
        return compositionPorts;
    }

    public void setCompositionPorts(List<CompositionPortDto> compositionPorts) {
        this.compositionPorts = compositionPorts;
    }

    public List<ErrorModeDto> getErrorModes() {
        return errorModes;
    }

    public void setErrorModes(List<ErrorModeDto> errorModes) {
        this.errorModes = errorModes;
    }

    public List<PropagationPortDto> getPropagationPorts() {
        return propagationPorts;
    }

    public void setPropagationPorts(List<PropagationPortDto> propagationPorts) {
        this.propagationPorts = propagationPorts;
    }
}
