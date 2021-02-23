package it.unifi.stlab.fault2failure.knowledge.composition;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "compositionPorts")
public class CompositionPort {
    @ManyToOne
    private final Component parent;
    @ManyToOne
    private final Component child;
    @Id
    private final UUID uuid = UUID.randomUUID();

    /**
     * Create a CompositionPort by passing the parent of the hierarchical structure
     *
     * @param child  reference of the Component in the hierarchical structure of the system that acts as the child
     * @param parent reference of the Component in the hierarchical structure of the system that acts as the parent
     */
    public CompositionPort(Component child, Component parent) {
        this.parent = parent;
        this.child = child;
    }

    public CompositionPort() {
        this.parent = null;
        this.child = null;
    }

    public Component getParent() {
        return this.parent;
    }

    public Component getChild() {
        return this.child;
    }

}
