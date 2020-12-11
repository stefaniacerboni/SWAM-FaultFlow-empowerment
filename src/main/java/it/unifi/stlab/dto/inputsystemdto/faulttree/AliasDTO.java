package it.unifi.stlab.dto.inputsystemdto.faulttree;

public class AliasDTO {
    private String gate;
    private String faultName;
    //private double prob;

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }
}
