package it.unifi.stlab.dto.system;

import java.util.List;

public class MetaComponentDto {
    private String name;
    private CompositionPortDto compositionPort;
    private List<ErrorModeDto> errorModes;
    private List<PropagationPortDto> propagationPorts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompositionPortDto getCompositionPort() {
        return compositionPort;
    }

    public void setCompositionPort(CompositionPortDto compositionPort) {
        this.compositionPort = compositionPort;
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
