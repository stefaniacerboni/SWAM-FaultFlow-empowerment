package it.unifi.stlab.fault2failure.knowledge.propagation;

import javax.persistence.Entity;
import javax.persistence.Table;

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
}