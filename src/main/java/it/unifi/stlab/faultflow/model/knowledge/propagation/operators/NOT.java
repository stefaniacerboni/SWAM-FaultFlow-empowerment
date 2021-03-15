package it.unifi.stlab.faultflow.model.knowledge.propagation.operators;

import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;

import java.util.ArrayList;

public class NOT extends Operator {
    public NOT() {
        this.elements = new ArrayList<>();
    }

    /**
     * NOT is a unary operator, so it must have just one element into its elements list.
     * This means that when it's requested to add another child to the same NOT operator,
     * FIRST it deletes the element it already has then it adds the new one
     *
     * @param be the booleanExpression to be added under the NOT operator
     */
    public void addChild(BooleanExpression be) {

        if (this.elements.size() != 0)
            this.removeChild(this.elements.get(0));
        elements.add(be);
    }

    /**
     * Method compute calculates the booleanExpression in a recursive way by calling the compute() method in every
     * child of the Operator.
     *
     * @return a boolean value. For the NOT operator: returns true if its child is in a false state. Otherwise it returns false.
     */
    @Override
    public boolean compute() {
        return !elements.get(0).compute();
    }

    @Override
    public String toString() {
        return "!" + elements.get(0).toString();
    }
}
