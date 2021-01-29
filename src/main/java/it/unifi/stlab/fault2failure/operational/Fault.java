package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.utils.SampleGenerator;

import java.math.BigDecimal;

public class Fault extends Event{
    private final FaultMode faultMode;
    boolean isActive;

    /**
     * Create a Failure from its description and FailureMode.
     * Timestamp is set as a default value of -1 unless method occurred() is called.
     * The Failure is set as not-active unless method occurred() is called.
     *
     * @param description a string which must be unique.
     * @param faultMode   the type of the Failure.
     */
    public Fault(String description, FaultMode faultMode, BigDecimal timestamp) {
        this.description = description;
        this.faultMode = faultMode;
        this.timestamp = timestamp;
    }

    public Fault(String description, EndogenousFaultMode faultMode) {
        this.description = description;
        this.faultMode = faultMode;
        this.timestamp = BigDecimal.valueOf(SampleGenerator.generate(faultMode.getArisingPDFToString()));
    }

    /**
     * Method occurred specifies when a Failure is expected to occur. Set the FailureMode state to true as a consequence.
     *
     */
    public void occurred() {
        this.faultMode.setState(true);
    }

    /**
     * Method deactivate set the FailureMode state to false and, as its name says, deactivates the Failure.
     */
    public void deactivate() {
        this.isActive = false;
        //this.failureMode.setState(false);
    }
    public FaultMode getFaultMode() {
        return this.faultMode;
    }


}
