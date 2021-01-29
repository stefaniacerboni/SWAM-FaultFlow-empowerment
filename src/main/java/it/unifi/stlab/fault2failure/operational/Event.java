package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;

import java.math.BigDecimal;

public abstract class Event {
    String description;
    BigDecimal timestamp;

    public String getDescription() {
        return description;
    }

    public BigDecimal getTimestamp() {
        return this.timestamp;
    }

    public String toString() {
        return this.getDescription() + " = " + this.getTimestamp();
    }
}
