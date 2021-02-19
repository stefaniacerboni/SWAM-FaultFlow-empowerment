package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;

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
