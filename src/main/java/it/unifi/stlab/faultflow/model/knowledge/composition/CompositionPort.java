package it.unifi.stlab.faultflow.model.knowledge.composition;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "compositionports")
public class CompositionPort extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_fk")
    private final Component parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_fk")
    private final Component child;

    /**
     * Create a CompositionPort by passing the parent of the hierarchical structure
     *
     * @param parent reference of the Component in the hierarchical structure of the system that acts as the parent
     * @param child reference of the Component in the hierarchical structure of the system that acts as the child
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

    /*
     * Add a child to the hierarchical structure represented and set its CompositionPort to this.
     *
     * @param component An instance of MetaComponent to be added as a child of the parent in the hierarchy

    public void addChild(Component... component) {
        this.children.addAll(Arrays.asList(component));
        for (Component mc : component)
            mc.setCompositionPorts(this);
    }

     */
}
