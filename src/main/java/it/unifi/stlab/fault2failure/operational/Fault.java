package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.utils.SampleGenerator;

import java.math.BigDecimal;

public class Fault extends Event {
    private final FaultMode faultMode;

    /**
     * Create a Fault from its description and FaultMode and the moment in which it's expected to occur.
     *
     * @param description a string which must be unique.
     * @param faultMode   the type of the Failure.
     * @param timestamp   the moment in which the Fault it's expected to occur
     */
    public Fault(String description, FaultMode faultMode, BigDecimal timestamp) {
        super.setDescription(description);
        this.faultMode = faultMode;
        super.setTimestamp(timestamp);
    }

    /**
     * Create a Fault just from its description and FaultMode. This can be done only for endogenousFaults
     * (which means, basic events in the FaultTree). By doing so, the timestamp is automatically generated
     * by sampling the faultMode's PDF.
     *
     * @param description a string which must be unique.
     * @param faultMode   the type of the Failure.
     */

    public Fault(String description, EndogenousFaultMode faultMode) {
        super.setDescription(description);
        BigDecimal timestamp = BigDecimal.valueOf(SampleGenerator.generate(faultMode.getArisingPDFToString()));
        super.setTimestamp(timestamp);
        this.faultMode = faultMode;

    }

    /**
     * Method occurred is called when a Failure is expected to occur.
     * Sets the FailureMode state to true as a consequence.
     */
    public void occurred() {
        this.faultMode.setState(true);
    }

    public FaultMode getFaultMode() {
        return this.faultMode;
    }


}
