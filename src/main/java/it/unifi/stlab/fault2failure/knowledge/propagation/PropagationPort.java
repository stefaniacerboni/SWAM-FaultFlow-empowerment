package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;

public class PropagationPort {
    private final FailureMode propagatedFailureMode;
    private final ExogenousFaultMode exogenousFaultMode; // TODO diventa exogenousFaultMode
    //private final ErrorMode errorMode;
    private final MetaComponent affectedComponent; // TODO diventa affectedComponent

    /**
     * Add a propagationPort by specifying four parameters: the inputFail already happened, the outputFault in which
     * the inputFail propagates, the ErrorMode that handles this propagation and the MetaComponent affected by this propagation.
     * @param inFail the FailureMode that triggers the propagation.
     * @param outFault the FailureMode in which the inputFail propagates
     * @param affectedComponent the MetaComponent affected by the propagation. This means that the outputFault specified
     *                      is one of the FailureModes that could happen inside this metaComponent.
     */
    /*
    public PropagationPort(FailureMode inFail, FailureMode outFail, ErrorMode errorMode, MetaComponent metaComponent){
        this.inFail = inFail;
        this.outFail = outFail;
        this.errorMode =errorMode;
        this.metaComponent = metaComponent;
    }*/
    public PropagationPort(FailureMode inFail, ExogenousFaultMode outFault, MetaComponent affectedComponent){
        this.propagatedFailureMode=inFail;
        this.exogenousFaultMode=outFault;
        this.affectedComponent=affectedComponent;
    }

    public FailureMode getPropagatedFailureMode() {
        return propagatedFailureMode;
    }

    public ExogenousFaultMode getExogenousFaultMode() {
        return exogenousFaultMode;
    }

    public MetaComponent getAffectedComponent() {
        return affectedComponent;
    }
}
