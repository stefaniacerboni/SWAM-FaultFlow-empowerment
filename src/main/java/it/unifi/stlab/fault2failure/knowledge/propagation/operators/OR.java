package it.unifi.stlab.fault2failure.knowledge.propagation.operators;

import it.unifi.stlab.fault2failure.knowledge.propagation.BooleanExpression;

import java.util.ArrayList;

public class OR extends Operator {

    public OR() {
        elements = new ArrayList<>();
    }

    public void addChild(BooleanExpression be) {
        elements.add(be);
    }
    /**
     * Method compute() calculates the booleanExpression in a recursive way by calling the compute() method in every
     * child of the Operator.
     * @return a boolean value. For the OR operator: returns true if even just one of the children of the operator is in a true state.
     */
    @Override
    public boolean compute() {
        for (BooleanExpression e : elements) {
            if (e.compute())
                return true;
        }
        return false;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(BooleanExpression be : elements){
            sb.append(be.toString()).append(")||(");
        }
        return sb.substring(0, sb.length()-3);
    }
}
