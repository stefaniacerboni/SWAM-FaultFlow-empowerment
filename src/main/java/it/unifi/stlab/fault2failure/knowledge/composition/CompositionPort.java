package it.unifi.stlab.fault2failure.knowledge.composition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositionPort {
    private final Component parent;
    private final Component child;
    @Id
    private final UUID uuid = UUID.randomUUID();

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

    public Component getParent() {
        return this.parent;
    }

    public Component getChild() {
        return this.child;
    }

}
