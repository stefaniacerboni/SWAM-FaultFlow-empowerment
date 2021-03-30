package it.unifi.stlab.faultflow.model.knowledge.propagation;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "exogenousFaultModes")
public class ExogenousFaultMode extends FaultMode {
    public ExogenousFaultMode() {
        super.name = "";
        super.setState(false);
    }

    public ExogenousFaultMode(String name) {
        this.name = name;
        super.setState(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExogenousFaultMode faultMode = (ExogenousFaultMode) o;
        return uuid.equals(faultMode.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }
}
