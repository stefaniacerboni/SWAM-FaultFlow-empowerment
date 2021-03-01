package it.unifi.stlab.dto.inputsystemdto.faulttree;

public class AliasDto {
    private String componentId;
    private String faultName;
    private Double routingProbability;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public Double getRoutingProbability() {
        return routingProbability;
    }

    public void setRoutingProbability(Double routingProbability) {
        this.routingProbability = routingProbability;
    }
}
