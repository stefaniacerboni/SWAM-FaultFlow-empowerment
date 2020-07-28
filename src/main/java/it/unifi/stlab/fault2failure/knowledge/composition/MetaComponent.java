package it.unifi.stlab.fault2failure.knowledge.composition;


public class MetaComponent {
    private String name;
    private CompositionPort compositionPort;

    /**
     * Create a MetaComponent by setting its name to the String passed in input and its CompositionPort to null
     * @param name It's the name of the ComponentType we want to describe through a MetaComponent.
     */
    public MetaComponent(String name){
        this.name = name;
        this.compositionPort=null;
    }

    public String getName(){
        return this.name;
    }
    public CompositionPort getCompositionPort(){return this.compositionPort;}

    /**
     * Set the CompositionPort in which the MetaComponent appears as the Parent or as one of the children.
     * This method is always called from CompositionPort class. Thus it is package-protected.
     * @param cp This MetaComponent's CompositionPort.
     */
    protected void setCompositionPort(CompositionPort cp){
        this.compositionPort = cp;
    }
}
