package it.unifi.stlab.faultflow.model.knowledge.propagation.operators;

import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class
 */
public abstract class Operator implements BooleanExpression {
    protected List<BooleanExpression> elements;

    /**
     * Method removeChild removes a FailureMode or an Operator from the BooleanExpression.
     *
     * @param be the operator or FailureMode to be removed
     * @return be if successfully deletes the element, else returns null.
     */
    @Override
    public BooleanExpression removeChild(BooleanExpression be) {
        for (BooleanExpression elem : elements) {
            if (elem == be) {
                elements.remove(elem);
                return be;
            } else {
                //check into Operator's children too
                if (elem instanceof Operator)
                    if (elem.removeChild(be) != null)
                        return be;
            }
        }
        return null;
    }

    /**
     * @return a list of the FailureModes that appears inside the BooleanExpression.
     * In a tree-perspective: returns all the leaves inside the tree.
     */
    public List<FaultMode> extractIncomingFaults() {
        List<FaultMode> incomingFails = new ArrayList<>();
        for (BooleanExpression be : elements) {
            if (be instanceof FaultMode)
                incomingFails.add((FaultMode) be);
            else {
                incomingFails.addAll(be.extractIncomingFaults());
            }
        }
        return incomingFails;
    }

    public List<BooleanExpression> getElements() {
        return elements;
    }
}
