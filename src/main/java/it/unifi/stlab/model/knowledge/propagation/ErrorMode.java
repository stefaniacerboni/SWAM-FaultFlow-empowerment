package it.unifi.stlab.model.knowledge.propagation;

import it.unifi.stlab.model.knowledge.composition.Component;
import it.unifi.stlab.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ErrorMode {
    private final String name;
    private BooleanExpression activationFunction;
    private FailureMode outgoingFailure;
    private final List<FaultMode> inputFaultModes;
    private RealDistribution timetofailurePDF;

    /**
     * Create and ErrorMode by saying its name and its EnablingFunction (or ActivationFunction).
     * The CDF can be set later by calling its setter.
     *
     * @param name     ErrorMode's Name. It'll be used in the PetriNet translation of the model, to give a name to its corresponding place.
     *                 Must be unique.
     * @param function the ActivationFunction (or enablingFunction) of the ErrorMode, expressed as an instance of BooleanExpression.
     */
    public ErrorMode(String name, BooleanExpression function) {
        this.name = name;
        this.activationFunction = function;
        this.inputFaultModes = activationFunction.extractIncomingFaults();
        this.outgoingFailure = null;
    }

    public ErrorMode(String name) {
        this.name = name;
        this.activationFunction = null;
        this.inputFaultModes = new ArrayList<>();
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure) {
        this(name, function);
        this.outgoingFailure = outgoingFailure;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, RealDistribution timetofailurePDF) {
        this(name, function, outgoingFailure);
        this.timetofailurePDF = timetofailurePDF;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF) {
        this(name, function, outgoingFailure);
        setPDF(timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, RealDistribution timetofailurePDF, Component component) {
        this(name, function, outgoingFailure, timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF, Component component) {
        this(name, function, outgoingFailure, timetofailurePDF);
    }

    public String getName() {
        return this.name;
    }

    public String getActivationFunction() {
        return activationFunction.toString();
    }

    public List<FaultMode> getInputFaultModes() {
        return inputFaultModes;
    }

    public FailureMode getOutgoingFailure() {
        return this.outgoingFailure;
    }

    public RealDistribution getTimetofailurePDF() {
        return this.timetofailurePDF;
    }

    public void setPDF(String timetofailurePDF) {
        this.timetofailurePDF = PDFParser.parseStringToRealDistribution(timetofailurePDF);
    }

    public void setOutGoingFailure(FailureMode fm) {
        this.outgoingFailure = fm;
    }

    public boolean checkActivationFunction() {
        return activationFunction.compute();
    }

    public boolean checkFaultIsPresent(String name) {
        return this.inputFaultModes.stream().anyMatch(x -> x.name.equals(name));
    }

    public void addInputFaultMode(FaultMode... faultMode) {
        this.inputFaultModes.addAll(Arrays.asList(faultMode));
    }

    public void addOutputFailureMode(FailureMode failureMode) {
        this.outgoingFailure = failureMode;
    }

    public void setEnablingCondition(String booleanExpression, HashMap<String, FaultMode> faultModes) {
        this.activationFunction = BooleanExpression.config(booleanExpression, faultModes);
    }
}
