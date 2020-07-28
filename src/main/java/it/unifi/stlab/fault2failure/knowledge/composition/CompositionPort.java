package it.unifi.stlab.fault2failure.knowledge.composition;

import java.util.ArrayList;
import java.util.List;

public class CompositionPort {
    private MetaComponent parent;
    private List<MetaComponent> children;

    /**
     * Create a CompositionPort by passing the parent of the hierarchical structure
     * @param parent
     */
    public CompositionPort(MetaComponent parent){
        this.parent = parent;
        this.children = new ArrayList<>();
        parent.setCompositionPort(this);
    }

    /**
     * Add a child to the hierarchical structure represented and set its CompositionPort to this.
     * @param metaComponent An instance of MetaComponent to be added as a child of the parent in the hierarchy
     */
    public void addChild(MetaComponent metaComponent){
        this.children.add(metaComponent);
        metaComponent.setCompositionPort(this);
    }

    public MetaComponent getParent(){
        return this.parent;
    }
    public List<MetaComponent> getChildren(){return this.children;}
}
