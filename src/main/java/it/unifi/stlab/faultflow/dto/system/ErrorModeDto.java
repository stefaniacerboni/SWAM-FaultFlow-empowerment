package it.unifi.stlab.faultflow.dto.system;

import java.util.List;

public class ErrorModeDto {
    private String name;
    private String activationFunction;
    private String outgoingFailure;
    private List<FaultModeDto> inputFaultModes;
    private String timetofailurePDF;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(String activationFunction) {
        this.activationFunction = activationFunction;
    }

    public String getOutgoingFailure() {
        return outgoingFailure;
    }

    public void setOutgoingFailure(String outgoingFailure) {
        this.outgoingFailure = outgoingFailure;
    }

    public List<FaultModeDto> getInputFaultModes() {
        return inputFaultModes;
    }

    public void setInputFaultModes(List<FaultModeDto> inputFaultModes) {
        this.inputFaultModes = inputFaultModes;
    }

    public String getTimetofailurePDF() {
        return timetofailurePDF;
    }

    public void setTimetofailurePDF(String timetofailurePDF) {
        this.timetofailurePDF = timetofailurePDF;
    }
}
