package it.unifi.stlab.faultflow.model.knowledge.propagation.operators;

import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;

import java.util.ArrayList;

public class KofN extends Operator {
    private final int k;
    private final int n;

    public KofN(int k, int n) {
        if (k > n || k <= 0)
            throw new IllegalArgumentException("K can't be negative, zero, or > N");
        else {
            this.k = k;
            this.n = n;
            this.elements = new ArrayList<>();
        }
    }

    public void addChild(BooleanExpression be) {
        if (this.elements.size() < n)
            elements.add(be);
        else
            throw new IllegalCallerException("Too many elements: there are already " + n + " FailureModes");

    }

    /**
     * Method compute calculates the booleanExpression in a recursive way by calling the compute() method in every
     * child of the Operator.
     *
     * @return a boolean value. For the K out of N operator:
     * -returns true if its only child has been set to true at least k times.
     * -return true if its children has been set to true overall at least k times.
     * -else returns false.
     */
    @Override
    public boolean compute() {
        int sum = 0;
        for (BooleanExpression fm : this.elements)
            if (fm.compute())
                sum++;
        return sum >= k;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (k == 1) {
            res.append(this.elements.get(0).toString());
            for (int i = 1; i < this.n; i++) {
                res.append("||").append(this.elements.get(i).toString()).append("||");
            }
        } else if (k == n) {
            res.append(this.elements.get(0).toString());
            for (int i = 1; i < this.n; i++) {
                res.append("&&").append(this.elements.get(i).toString());
            }
        } else {
            for (int i = 0; i <= n - k; i++) {
                BooleanExpression first = elements.get(i);
                for (int z = i + 1; z <= n - k + 1; z++) {
                    res.append("(");
                    res.append(first.toString());
                    BooleanExpression next;
                    for (int j = 0; j < k - 1; j++) {
                        next = elements.get(z + j);
                        res.append("&&").append(next.toString());
                    }
                    res.append(")");
                    res.append("||");
                }
            }
            res = new StringBuilder(res.substring(0, res.length() - 2));
        }
        return res.toString();
    }

    @Override
    public String toSimpleString() {
        StringBuilder res = new StringBuilder();
        if (k == 1) {
            res.append(this.elements.get(0).toSimpleString());
            for (int i = 1; i < this.n; i++) {
                res.append("||").append(this.elements.get(i).toSimpleString()).append("||");
            }
        } else if (k == n) {
            res.append(this.elements.get(0).toSimpleString());
            for (int i = 1; i < this.n; i++) {
                res.append("&&").append(this.elements.get(i).toSimpleString());
            }
        } else {
            for (int i = 0; i <= n - k; i++) {
                BooleanExpression first = elements.get(i);
                for (int z = i + 1; z <= n - k + 1; z++) {
                    res.append("(");
                    res.append(first.toSimpleString());
                    BooleanExpression next;
                    for (int j = 0; j < k - 1; j++) {
                        next = elements.get(z + j);
                        res.append("&&").append(next.toSimpleString());
                    }
                    res.append(")");
                    res.append("||");
                }
            }
            res = new StringBuilder(res.substring(0, res.length() - 2));
        }
        return res.toString();
    }
}
