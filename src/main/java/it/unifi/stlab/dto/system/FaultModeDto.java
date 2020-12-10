package it.unifi.stlab.dto.system;

public class FaultModeDto {
    private FaultType faultType;
    private String name;
    private String arisingPDF;

    public FaultType getFaultType() {
        return faultType;
    }

    public void setFaultType(FaultType faultType) {
        this.faultType = faultType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArisingPDF() {
        return arisingPDF;
    }

    public void setArisingPDF(String arisingPDF) {
        this.arisingPDF = arisingPDF;
    }
}
