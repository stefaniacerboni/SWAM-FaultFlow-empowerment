package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;

import java.math.BigDecimal;

public class Error extends Event{
    private final ErrorMode errorMode;

    public Error(String description, ErrorMode errorMode, BigDecimal timestamp){
        this.description = description;
        this.errorMode=errorMode;
        this.timestamp = timestamp;
    }

    public ErrorMode getErrorMode() {
        return errorMode;
    }
}
