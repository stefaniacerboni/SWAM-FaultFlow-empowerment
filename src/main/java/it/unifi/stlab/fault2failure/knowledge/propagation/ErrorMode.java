package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ErrorMode {
    private final String name;
    private BooleanExpression activationFunction;
    private FailureMode outgoingFailure;
    private final List<FaultMode> inputFaultModes; // DONE questa diventa una lista di FaultMode
    private StochasticTransitionFeature timetofailurePDF;

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

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, StochasticTransitionFeature timetofailurePDF) {
        this(name, function, outgoingFailure);
        this.timetofailurePDF = timetofailurePDF;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF) {
        this(name, function, outgoingFailure);
        setPDF(timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, StochasticTransitionFeature timetofailurePDF, MetaComponent metaComponent) {
        this(name, function, outgoingFailure, timetofailurePDF);
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, String timetofailurePDF, MetaComponent metaComponent) {
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

    public StochasticTransitionFeature getTimetofailurePDF() {
        return this.timetofailurePDF;
    }

    public void setPDF(String timetofailurePDF) {
        String typePDF = timetofailurePDF.replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = timetofailurePDF.substring(typePDF.length() + 1, timetofailurePDF.length() - 1);
        String[] args;
        switch (typePDF.toLowerCase()) {
            case "uniform":
                //two arguments
                args = arguments.split(",");
                this.timetofailurePDF = StochasticTransitionFeature.newUniformInstance(args[0].trim(), args[1].trim());
                break;
            case "dirac":
                //one argument
                this.timetofailurePDF = StochasticTransitionFeature.newDeterministicInstance(arguments);
                //or this.timetofailurePDF = StochasticTransitionFeature.newUniformInstance(arguments, arguments) //TODO ask
                break;
            case "exp":
                //one argument
                this.timetofailurePDF = StochasticTransitionFeature.newExponentialInstance(arguments);
                break;
            case "erlang":
                args = arguments.split(",");
                this.timetofailurePDF = StochasticTransitionFeature.newErlangInstance(Integer.parseInt(args[0].trim()), args[1].trim());
                break;
            default:
                throw new UnsupportedOperationException("PDF not supported");
        }
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
