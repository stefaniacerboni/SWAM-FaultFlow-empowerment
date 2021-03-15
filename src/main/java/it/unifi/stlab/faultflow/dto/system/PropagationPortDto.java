package it.unifi.stlab.faultflow.dto.system;

public class PropagationPortDto {
    private String propagatedFailureMode;
    private String exogenousFaultMode;
    private String affectedComponent;
    private double routingProbability = 1.0;

    public String getPropagatedFailureMode() {
        return propagatedFailureMode;
    }

    public void setPropagatedFailureMode(String propagatedFailureMode) {
        this.propagatedFailureMode = propagatedFailureMode;
    }

    public String getExogenousFaultMode() {
        return exogenousFaultMode;
    }

    public void setExogenousFaultMode(String exogenousFaultMode) {
        this.exogenousFaultMode = exogenousFaultMode;
    }

    public String getAffectedComponent() {
        return affectedComponent;
    }

    public void setAffectedComponent(String affectedComponent) {
        this.affectedComponent = affectedComponent;
    }

    public double getRoutingProbability() {
        return routingProbability;
    }

    public void setRoutingProbability(double routingProbability) {
        this.routingProbability = routingProbability;
    }
}
