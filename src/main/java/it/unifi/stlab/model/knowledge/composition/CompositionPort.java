package it.unifi.stlab.model.knowledge.composition;

public class CompositionPort {
    private final Component parent;
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
