package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import java.util.List;

public class ErrorMode {
    private String name;
    private final BooleanExpression activationFunction;
    private FailureMode outgoingFailure;
    private List<FaultMode> inputFaultModes; // DONE questa diventa una lista di FaultMode
    private StochasticTransitionFeature timetofailurePDF;
    private MetaComponent metaComponent;

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
        this.inputFaultModes = activationFunction.extractIncomingFaults();
        this.outgoingFailure = null;
        this.metaComponent= null;

    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure){
        this(name, function);
        this.outgoingFailure = outgoingFailure;
    }
    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, StochasticTransitionFeature timetofailurePDF){
        this(name, function, outgoingFailure);
        this.timetofailurePDF = timetofailurePDF;
    }

    public ErrorMode(String name, BooleanExpression function, FailureMode outgoingFailure, StochasticTransitionFeature timetofailurePDF, MetaComponent metaComponent){
        this(name, function, outgoingFailure, timetofailurePDF);
        this.metaComponent=metaComponent;
    }

    public String getName(){return this.name;}
    public String getActivationFunction(){return activationFunction.toString();}
    public List<FaultMode> getInputFaultModes() {
        return inputFaultModes;
    }
    public FailureMode getOutgoingFailure(){
        return this.outgoingFailure;
    }
    public StochasticTransitionFeature getTimetofailurePDF(){return this.timetofailurePDF;}

    public void setPDF(String timetofailurePDF) {
        String typePDF = timetofailurePDF.replaceAll("\\s*\\([^()]*\\)\\s*", "");
        String arguments = timetofailurePDF.substring(typePDF.length()+1, timetofailurePDF.length()-1);
        String[] args;
        switch (typePDF) {
            case "uniform":
                //two arguments
                args = arguments.split(",");
                this.timetofailurePDF = StochasticTransitionFeature.newUniformInstance(args[0], args[1]);
            case "dirac":
                //one argument
                this.timetofailurePDF = StochasticTransitionFeature.newDeterministicInstance(arguments);
            case "exp":
                //one argument
                this.timetofailurePDF = StochasticTransitionFeature.newExponentialInstance(arguments);
            case "erlang":
                args = arguments.split(",");
                this.timetofailurePDF = StochasticTransitionFeature.newErlangInstance(Integer.parseInt(args[0]), args[1]);
        }
    }

    public void setOutGoingFailure(FailureMode fm){
        this.outgoingFailure=fm;
    }

    public MetaComponent getMetaComponent() {
        return metaComponent;
    }

    public boolean checkActivationFunction(){
        return activationFunction.compute();
    }

    public boolean checkFaultIsPresent(String name){
        return this.inputFaultModes.stream().anyMatch(x-> x.name.equals(name));
    }
}
