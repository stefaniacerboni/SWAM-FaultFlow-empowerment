package it.unifi.stlab.stateflow;
import java.util.Map;
import java.util.Stack;

public class BooleanExpressionParser {
    private final Map<String, Node> nodes;

    public BooleanExpressionParser(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public boolean evaluate(String expression) {
        if (expression == null || expression.isEmpty()) {
            return true;
        }
        return evaluateExpression(expression.replaceAll("\\s+", ""));
    }

    private boolean evaluateExpression(String expression) {
        Stack<Boolean> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue; // Skip whitespace
            }

            if (c == '(') {
                operators.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operators.peek().equals("(")) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Pop '('
            } else if (c == '&' || c == '|') {
                String operator = String.valueOf(c);
                if (i + 1 < expression.length() && expression.charAt(i + 1) == c) {
                    operator += expression.charAt(++i); // Handle && and ||
                }
                while (!operators.isEmpty() && hasPrecedence(operator, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(operator);
            } else if (c == '!') {
                boolean value = evaluateLiteral(expression.substring(++i));
                values.push(!value);
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isLetterOrDigit(expression.charAt(i)) || expression.charAt(i) == '_')) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                String nodeName = sb.toString();
                values.push(evaluateLiteral(nodeName));
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean applyOperator(String operator, boolean b, boolean a) {
        switch (operator) {
            case "&&":
                return a && b;
            case "||":
                return a || b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private boolean evaluateLiteral(String literal) {
        if (literal.startsWith("!")) {
            return !((Boolean) nodes.get(literal.substring(1)).getState("active"));
        }
        return (Boolean) nodes.get(literal).getState("active");
    }

    private boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")")) {
            return false;
        }
        if ((op1.equals("&&") || op1.equals("||")) && (op2.equals("!") || op2.equals("&&") || op2.equals("||"))) {
            return false;
        }
        return true;
    }
}
