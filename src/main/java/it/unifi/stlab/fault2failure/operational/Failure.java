package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.translator.Translator;

import java.math.BigDecimal;

public class Failure {
    private String description;
    private FailureMode failureMode;
    private BigDecimal timestamp;
    private boolean isActive;

    /**
     * Create a Failure from its description and FailureMode.
     * Timestamp is set as a default value of -1 unless method occurred() is called.
     * The Failure is set as not-active unless method occurred() is called.
     * @param description a string which must be unique.
     * @param failureMode the type of the Failure.
     */
    public Failure(String description, FailureMode failureMode){
        this.description=description;
        this.failureMode=failureMode;
        this.isActive = false;
        this.timestamp=BigDecimal.valueOf(-1);
    }
    public String getDescription(){
        return this.description;
    }
    public FailureMode getFailureMode(){
        return this.failureMode;
    }
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method occurred specifies when a Failure is expected to occur. Set the FailureMode state to true as a consequence.
     * @param timestamp gives the time of the failure occurrence
     */
    public void occurred(BigDecimal timestamp){
        this.isActive=true;
        this.failureMode.setState(true);
        this.timestamp = timestamp;
    }

    /**
     * Method deactivate set the FailureMode state to false and, as its name says, deactivates the Failure.
     */
    public void deactivate(){
        this.isActive=false;
        this.failureMode.setState(false);
    }
    public BigDecimal getTimestamp(){
        return this.timestamp;
    }
    public String toString(){
        return this.getDescription()+" = "+this.getTimestamp();
    }

}
