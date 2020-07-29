package it.unifi.stlab.fault2failure.knowledge.propagation;

import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import java.util.List;

public class ErrorMode {
    private String name;
    private final BooleanExpression activationFunction;
    private FailureMode outgoingFailure;
    private List<FailureMode> inputFaultModes; // TODO questa diventa una lista di FaultMode
    private StochasticTransitionFeature timetofailurePDF;

    /**
     * Create and ErrorMode by saying its name and its EnablingFunction (or ActivationFunction).
     * The CDF can be set later by calling its setter.
     * @param name ErrorMode's Name. It'll be used in the PetriNet translation of the model, to give a name to its corresponding place.
     *             Must be unique.
     * @param function the ActivationFunction (or enablingFunction) of the ErrorMode, expressed as an instance of BooleanExpression.
     */
    public ErrorMode(String name, BooleanExpression function){
        this.name=name;
        this.activationFunction = function;
        this.inputFaultModes = activationFunction.extractIncomingFails();
        this.outgoingFailure = null;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure){
        this(name, function);
        this.outgoingFailure = outgoingFailure;
    }
    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, StochasticTransitionFeature timetofailurePDF){
        this(name, function, outgoingFailure);
        this.timetofailurePDF = timetofailurePDF;
    }

    public String getName(){return this.name;}
    public String getActivationFunction(){return activationFunction.toString();}
    public List<FailureMode> getInputFaultModes() {
        return inputFaultModes;
    }
    public FailureMode getOutgoingFailure(){
        return this.outgoingFailure;
    }
    public StochasticTransitionFeature getTimetofailurePDF(){return this.timetofailurePDF;}
    public void setOutGoingFailure(FailureMode fm){
        this.outgoingFailure=fm;
    }

    public boolean checkActivationFunction(){
        return activationFunction.compute();
    }
}
