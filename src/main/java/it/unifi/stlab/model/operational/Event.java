package it.unifi.stlab.model.operational;

import java.math.BigDecimal;

public abstract class Event {
    private String description;
    private BigDecimal timestamp;

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTimestamp() {
        return this.timestamp;
    }

    protected void setTimestamp(BigDecimal timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return this.getDescription() + " = " + this.getTimestamp();
    }
}
