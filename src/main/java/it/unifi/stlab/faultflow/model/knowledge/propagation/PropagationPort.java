package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "propagationports")
public class PropagationPort extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "failure_mode_fk")
    private final FailureMode propagatedFailureMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fault_mode_fk")
    private final ExogenousFaultMode exogenousFaultMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_fk")
    private final Component affectedComponent;

    private final BigDecimal routingProbability;


    public PropagationPort(){
        this.exogenousFaultMode = null;
        this.affectedComponent = null;
        this.propagatedFailureMode = null;
        this.routingProbability = null;
    }

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
