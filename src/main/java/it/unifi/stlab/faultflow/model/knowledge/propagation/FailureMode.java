package it.unifi.stlab.faultflow.model.knowledge.propagation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "failureModes")
public class FailureMode {
    private final String description;
    @Id
    private final UUID uuid = UUID.randomUUID();


    public FailureMode() {
        this.description = "";
    }

    /**
     * Create a FailureMode by saying its description
     *
     * @param description a string describing the FailureMode. Must be unique.
     */
    public FailureMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Override toString Method inside BooleanExpression.
     *
     * @return A string that describes FailureMode's state in a way that resembles the enabling Functions in a Petri Net.
     */

    public String toString() {
        return this.description + ">0";
    }
}
