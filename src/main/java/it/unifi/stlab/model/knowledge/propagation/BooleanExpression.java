package it.unifi.stlab.model.knowledge.propagation;

import it.unifi.stlab.model.knowledge.propagation.operators.AND;
import it.unifi.stlab.model.knowledge.propagation.operators.KofN;
import it.unifi.stlab.model.knowledge.propagation.operators.NOT;
import it.unifi.stlab.model.knowledge.propagation.operators.OR;

import java.util.HashMap;
import java.util.List;

public interface BooleanExpression {

    /**
     * Method config transforms a BooleanExpression written as a String into a tree composed of Operator and
     * FailureMode. The Operator at the root of the tree is the last operator to be computed. Every Leaf is a FailureMode.
     *
     * @param booleanExpression is the string form of the booleanExpression, written in first order logic. The Operators supported are:
     *                          && as AND
     *                          || as OR
     *                          ! as NOT
     *                          K/N (i.e. 2/3) as the K out of N operator (i.e. 2 out of 3).
     * @param failModes         It's an Hashmap filled with the FailureModes expected in the System under test.
     *                          The hashmap could also be empty, it's config's job to populate it with the failureModes that
     *                          appear in the booleanExpression but are not in the hashmap yet.
     * @return an instance of BooleanExpression which could be described as a Tree composed of instances of Operator and FailureMode.
     */
    static BooleanExpression config(String booleanExpression, HashMap<String, FaultMode> failModes) {
        //Preprocess string because some regex operations escapes with particular characters
        String newString = booleanExpression;
        newString = newString.replaceAll("&&", "&");
        newString = newString.replace("||", "°");
        return _config(newString, failModes);

    }

    private static BooleanExpression _config(String booleanExpression, HashMap<String, FaultMode> failModes) {
        BooleanExpression be;
        switch (findOuterOperator(booleanExpression)) {
            case '°':
                be = new OR();
                for (String literal : booleanExpression.split("°"))
                    be.addChild(_config(literal.replaceAll("[\\[\\](){}]", ""), failModes));
                return be;
            case '&':
                be = new AND();
                for (String literal : booleanExpression.split("&"))
                    be.addChild(_config(literal.replaceAll("[\\[\\](){}]", ""), failModes));
                return be;
            case '!':
                be = new NOT();
                be.addChild(_config(parseNOT(booleanExpression), failModes));
                return be;
            case '/':
                be = new KofN(Integer.parseInt(booleanExpression.substring(booleanExpression.indexOf("/") - 1, booleanExpression.indexOf("/"))),
                        Integer.parseInt(booleanExpression.substring(booleanExpression.indexOf("/") + 1, booleanExpression.indexOf("/") + 2)));
                String failNames = booleanExpression.substring(booleanExpression.indexOf("/") + 2);
                for (String literal : failNames.split(","))
                    be.addChild(_config(literal, failModes));
                return be;
            default:
                be = addFailure(booleanExpression, failModes);
        }
        return be;
    }

    /**
     * @param literal   name of the FailureMode to be added as a Leaf in the BooleanExpression
     * @param failModes list of the failureModes expected in the System under test, in which the method will add the FailureMode
     *                  expressed by @param literal
     * @return an instance of BooleanExpression to be added as a child in the composition pattern.
     */
    private static BooleanExpression addFailure(String literal, HashMap<String, FaultMode> failModes) {
        BooleanExpression be;
        String cleanName;
        //Se il literal contiene una negazione, va isolato il vero faultName:
        if (literal.indexOf('!') != -1) {
            //c'è un not tra parentesi, richiama il tutto senza parentesi
            //e metti il failure come figlio di NOT
            be = new NOT();
            be.addChild(addFailure(parseNOT(literal), failModes));
        } else {
            cleanName = literal.trim().replaceAll("[\\[\\](){}]", "");
            if (failModes.containsKey(cleanName))
                be = failModes.get(cleanName);
            else {
                be = new EndogenousFaultMode(cleanName);
                failModes.put(cleanName, (FaultMode) be);
            }
        }
        return be;
    }

    /**
     * As the NOT operator could be used with FailureModes alone (i.e. !A_Fault1) or with Operators (i.e. !(A_Fault1&&A_Fault2)) a
     * specific method was needed to support every special case and be able to extract correctly the name of the failureMode
     * under the NOT operator.
     *
     * @param booleanExpression the string from which the method will extract the names of the failureModes that appear in it.
     * @return the name of the FailureMode under the not operator (i.e. (!A_Fault1 && A_Fault2)-> return A_Fault1);
     */
    private static String parseNOT(String booleanExpression) {
        //clean booleanExpression from '!' first
        booleanExpression = booleanExpression.replace("!", "");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < booleanExpression.length(); i++) {
            switch (booleanExpression.charAt(i)) {
                case '(':
                    return booleanExpression.substring(i + 1, booleanExpression.indexOf(')'));
                case '^':
                case '°':
                    return res.toString();
                default:
                    res.append(booleanExpression.charAt(i));
            }
        }
        return res.toString();
    }

    /**
     * Finds the operator that will be at the root of the BooleanExpression tree. This means to find the outer operator,
     * or the operator that will be computer for last in the booleanExpression
     * (i.e. (A_Fault1)&&(A_Fault2||A_Fault3)-> &&(AND) will be the outer operator
     *
     * @param booleanExpression the string form of the booleanExpression in input
     * @return the character that represents the outer operator
     */
    private static char findOuterOperator(String booleanExpression) {
        //remove everything inside brackets
        String newBooleanExpression;
        while (booleanExpression.contains("(") && booleanExpression.contains(")")) {
            newBooleanExpression = booleanExpression.replaceAll("\\s*\\([^()]*\\)\\s*", "");
            if (newBooleanExpression.length() == 0)
                break;
            booleanExpression = newBooleanExpression;
        }
        //remove everything except special characters
        booleanExpression = booleanExpression.replaceAll("[^&°!/]", "");
        char operator;
        //take last operator
        if (booleanExpression.length() != 0)
            operator = booleanExpression.charAt(booleanExpression.length() - 1);
        else {
            operator = '.';
        }
        return operator;
    }

    default void addChild(BooleanExpression be) {
        throw new IllegalArgumentException("Cannot add in Leaf");
    }

    default BooleanExpression removeChild(BooleanExpression be) {
        throw new IllegalArgumentException("Cannot remove in Leaf");
    }

    default List<FaultMode> extractIncomingFaults() {
        return null;
    }

    boolean compute();

    String toString();
}
