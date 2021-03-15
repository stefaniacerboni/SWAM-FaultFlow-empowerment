package it.unifi.stlab.faultflow.model.knowledge.propagation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FaultMode implements BooleanExpression {

    @Id
    protected UUID uuid = UUID.randomUUID();

    protected String name;

    private boolean state;

    public void setState(boolean value) {
        state = value;
    }

    public boolean compute() {
        return state;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name + ">0";
    }
}
