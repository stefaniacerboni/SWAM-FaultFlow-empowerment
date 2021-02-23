package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "propagationPorts")
public class PropagationPort {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final FailureMode propagatedFailureMode;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final ExogenousFaultMode exogenousFaultMode;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Component affectedComponent;
    @Id
    private final UUID uuid = UUID.randomUUID();

    public PropagationPort() {
        this.propagatedFailureMode = null;
        this.exogenousFaultMode = null;
        this.affectedComponent = null;
    }

    /**
     * Add a propagationPort by specifying four parameters: the inputFail already happened, the outputFault in which
     * the inputFail propagates, the ErrorMode that handles this propagation and the MetaComponent affected by this propagation.
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
}
