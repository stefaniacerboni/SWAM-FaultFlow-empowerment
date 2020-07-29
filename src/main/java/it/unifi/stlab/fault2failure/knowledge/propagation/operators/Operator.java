package it.unifi.stlab.fault2failure.knowledge.propagation.operators;

import it.unifi.stlab.fault2failure.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class
 */
public abstract class Operator implements BooleanExpression {
    protected List<BooleanExpression> elements;

    /**
     * Method removeChild removes a FailureMode or an Operator from the BooleanExpression.
     * @param be the operator or FailureMode to be removed
     * @return be if successfully deletes the element, else returns null.
     */
    @Override
    public BooleanExpression removeChild(BooleanExpression be) {
        for (BooleanExpression elem : elements) {
            if(elem==be) {
                elements.remove(elem);
                return be;
            }
            else{
                //check into Operator's children too
                if (elem instanceof Operator)
                    if(elem.removeChild(be)!=null)
                        return be;
            }
        }
        return null;
    }

    /**
     * @return a list of the FailureModes that appears inside the BooleanExpression.
     *          In a tree-perspective: returns all the leaves inside the tree.
     */
    @Override
    public List<FailureMode> extractIncomingFails(){
        List <FailureMode> incomingFails = new ArrayList<>();
        for(BooleanExpression be: elements){
            if(be instanceof FailureMode)
                incomingFails.add((FailureMode)be);
            else{
                incomingFails.addAll(be.extractIncomingFails());
            }
        }
        return incomingFails;
    }

    public List<BooleanExpression> getElements() {
        return elements;
    }
}
