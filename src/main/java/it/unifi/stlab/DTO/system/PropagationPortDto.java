package it.unifi.stlab.DTO.system;

public class PropagationPortDto {
    private String propagatedFailureMode;
    private String exogenousFaultMode;
    private String affectedComponent;

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
}
