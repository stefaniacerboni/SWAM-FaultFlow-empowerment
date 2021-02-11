package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;

import java.math.BigDecimal;

public class Error extends Event{
    private final ErrorMode errorMode;

    public Error(String description, ErrorMode errorMode, BigDecimal timestamp){
        super.setDescription(description);
        this.errorMode=errorMode;
        super.setTimestamp(timestamp);
    }

    public ErrorMode getErrorMode() {
        return errorMode;
    }
}
