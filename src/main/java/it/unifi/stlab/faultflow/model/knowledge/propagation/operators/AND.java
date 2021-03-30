package it.unifi.stlab.faultflow.model.knowledge.propagation.operators;

import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;

import java.util.ArrayList;
import java.util.Objects;

public class AND extends Operator {

    public AND() {
        this.elements = new ArrayList<>();
    }

    public void addChild(BooleanExpression be) {
        elements.add(be);
    }

    /**
     * Method compute() calculates the booleanExpression in a recursive way by calling the compute() method in every
     * child of the Operator.
     *
     * @return a boolean value. For the AND operator: returns true if every child of the operator is in a true state.
     */
    @Override
    public boolean compute() {
        for (BooleanExpression e : elements) {
            if (!e.compute())
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (BooleanExpression be : elements) {
            sb.append(be.toString()).append(")&&(");
        }
        return sb.substring(0, sb.length() - 3);
    }

    @Override
    public String toSimpleString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (BooleanExpression be : elements) {
            sb.append(be.toSimpleString()).append(")&&(");
        }
        return sb.substring(0, sb.length() - 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AND operator = (AND) o;
        return Objects.equals(elements, operator.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}
