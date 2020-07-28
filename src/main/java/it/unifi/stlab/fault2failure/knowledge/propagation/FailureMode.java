package it.unifi.stlab.fault2failure.knowledge.propagation;

public class FailureMode implements BooleanExpression{
    private final String description;
    private Boolean state;

    /**
     * Create a FailureMode by saying its description
     * @param description a string describing the FailureMode. Must be unique.
     */
    public FailureMode(String description){
        this.description = description;
        this.state = false;
    }

    /**
     * Set FailureMode state to the booleans passed as parameter.
     * Also increase/decrease (according to the boolean passed) the counter inside the FailureMode,
     * Counter is needed for the K out of N Operator to know how many times a FailureMode has happened inside the system.
     * @param value the new state (true/false) of the FailureMode
     */
    public void setState(boolean value){
        state = value;
    }
    public String getDescription(){
        return description;
    }

    public boolean compute() {
        return state;
    }

    /**
     * Override toString Method inside BooleanExpression.
     * @return A string that describes FailureMode's state in a way that resembles the enabling Functions in a Petri Net.
     */

    public String toString(){
        return this.description+">0";
    }
}