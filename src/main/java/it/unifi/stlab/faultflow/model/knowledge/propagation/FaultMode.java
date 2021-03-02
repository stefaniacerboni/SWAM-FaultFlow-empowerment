package it.unifi.stlab.faultflow.model.knowledge.propagation;

public abstract class FaultMode implements BooleanExpression {

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
