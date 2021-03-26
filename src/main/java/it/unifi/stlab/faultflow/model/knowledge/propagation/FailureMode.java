package it.unifi.stlab.faultflow.model.knowledge.propagation;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "failuremodes")
public class FailureMode {
    @Id
    @Type(type = "uuid-char")
    private final UUID uuid = UUID.randomUUID();

    private final String description;

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

    public UUID getUuid() {
        return uuid;
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
