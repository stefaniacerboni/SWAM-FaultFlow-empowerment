package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.knowledge.composition.Component;

import java.math.BigDecimal;

public class PropagationPort {
    private final FailureMode propagatedFailureMode;
    private final ExogenousFaultMode exogenousFaultMode;
    private final Component affectedComponent;
    private final BigDecimal routingProbability;

    /**
     * Add a propagationPort by specifying four parameters: the inputFail already happened, the outputFault in which
     * the inputFail propagates, the MetaComponent affected by this propagation and (optional) the routing probability.
     *
     * @param inFail            the FailureMode that triggers the propagation.
     * @param outFault          the ExogenousFaultMode in which the inputFail propagates
     * @param affectedComponent the MetaComponent affected by the propagation. This means that the outputFault specified
     *                          is one of the FailureModes that could happen inside this metaComponent.
     */
    public PropagationPort(FailureMode inFail, ExogenousFaultMode outFault, Component affectedComponent) {
        this.propagatedFailureMode = inFail;
        this.exogenousFaultMode = outFault;
        this.affectedComponent = affectedComponent;
        this.routingProbability = BigDecimal.ONE;
    }

    public PropagationPort(FailureMode inFail, ExogenousFaultMode outFault, Component affectedComponent, BigDecimal routingProbability) {
        this.propagatedFailureMode = inFail;
        this.exogenousFaultMode = outFault;
        this.affectedComponent = affectedComponent;
        this.routingProbability = routingProbability;
    }

    public FailureMode getPropagatedFailureMode() {
        return propagatedFailureMode;
    }

    public ExogenousFaultMode getExogenousFaultMode() {
        return exogenousFaultMode;
    }

    public Component getAffectedComponent() {
        return affectedComponent;
    }

    public BigDecimal getRoutingProbability() {
        return routingProbability;
    }
}
