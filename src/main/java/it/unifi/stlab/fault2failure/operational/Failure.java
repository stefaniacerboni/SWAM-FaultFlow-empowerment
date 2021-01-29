package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;

import java.math.BigDecimal;

public class Failure extends Event{
    private FailureMode failureMode;

    public Failure(String description, FailureMode failureMode, BigDecimal timestamp){
        this.description = description;
        this.failureMode = failureMode;
        this.timestamp = timestamp;
    }

    public FailureMode getFailureMode() {
        return failureMode;
    }
}
