package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FaultMode extends BaseEntity implements BooleanExpression{

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

    public String toSimpleString(){
        return this.name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public List<FaultMode> extractIncomingFaults(){
        List<FaultMode> res = new ArrayList<>();
        res.add(this);
        return res;
    }
}
