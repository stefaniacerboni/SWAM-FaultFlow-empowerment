package it.unifi.stlab.faultflow.model.operational;

import it.unifi.stlab.faultflow.model.knowledge.propagation.FailureMode;

import java.math.BigDecimal;

public class Failure extends Event {
    private final FailureMode failureMode;

    public Failure(String description, FailureMode failureMode, BigDecimal timestamp) {
        super.setDescription(description);
        this.failureMode = failureMode;
        super.setTimestamp(timestamp);
    }

    public FailureMode getFailureMode() {
        return failureMode;
    }
}
