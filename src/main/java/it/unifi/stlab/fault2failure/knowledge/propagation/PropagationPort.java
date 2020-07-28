package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;

public class PropagationPort {
    private final FailureMode inFail;
    private final FailureMode outFail;
    private final ErrorMode errorMode;
    private final MetaComponent metaComponent;

    /**
     * Add a propagationPort by specifying four parameters: the inputFail already happened, the outputFault in which
     * the inputFail propagates, the ErrorMode that handles this propagation and the MetaComponent affected by this propagation.
     * @param inFail the FailureMode that triggers the propagation.
     * @param outFail the FailureMode in which the inputFail propagates
     * @param errorMode the ErrorMode that handles this propagation. This means that the ErrorMode specified has
     *              the inputFault as one of its incomingFailures and the outputFault as the outgoingFailure
     * @param metaComponent the MetaComponent affected by the propagation. This means that the outputFault specified
     *                      is one of the FailureModes that could happen inside this metaComponent.
     */
    public PropagationPort(FailureMode inFail, FailureMode outFail, ErrorMode errorMode, MetaComponent metaComponent){
        this.inFail = inFail;
        this.outFail = outFail;
        this.errorMode =errorMode;
        this.metaComponent = metaComponent;
    }

    /**
     * Add a propagationPort as before but without specifying the ErrorMode that handles the propagation.
     * This is needed when the propagation is immediate so, for example, when the failure that happens inside a
     * child component propagates into a fault of its parent component. The errorMode is set as default to null.
     * @param inFail the FailureMode that triggers the propagation.
     * @param outFail the FailureMode in which the inputFail propagates
     * @param metaComponent the MetaComponent affected by the propagation. This means that the outputFault specified
     *                      is one of the FailureModes that could happen inside this metaComponent.
     */
    public PropagationPort(FailureMode inFail, FailureMode outFail, MetaComponent metaComponent){
        this(inFail, outFail, null, metaComponent);
    }

    public FailureMode getFailOut(){
        return outFail;
    }

    public FailureMode getFailIn(){
        return inFail;
    }

    public ErrorMode getErrorMode(){
        return errorMode;
    }

    public MetaComponent getMetaComponent() {
        return metaComponent;
    }
}
