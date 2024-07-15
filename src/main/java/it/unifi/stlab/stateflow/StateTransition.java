package it.unifi.stlab.stateflow;

import org.apache.commons.math3.distribution.RealDistribution;
import java.util.function.Predicate;
import java.util.List;

public class StateTransition {
    private String id;
    private List<String> fromNodeIds; // Multiple input nodes
    private String toNodeId;
    private RealDistribution probabilityDistribution;
    private RealDistribution timeToFailureDistribution;
    private Predicate<List<Node>> condition;
    private double transientTimeWindow; // Time window for transient faults


    public StateTransition(String id, List<String> fromNodeIds, String toNodeId,
                           RealDistribution probabilityDistribution,
                           RealDistribution timeToFailureDistribution) {
        this.id = id;
        this.fromNodeIds = fromNodeIds;
        this.toNodeId = toNodeId;
        this.probabilityDistribution = probabilityDistribution;
        this.timeToFailureDistribution = timeToFailureDistribution;
        transientTimeWindow = 0.0;
    }

    public StateTransition(String id, List<String> fromNodeIds, String toNodeId,
                           RealDistribution probabilityDistribution,
                           RealDistribution timeToFailureDistribution,
                           double transientTimeWindow) {
        this(id, fromNodeIds, toNodeId, probabilityDistribution, timeToFailureDistribution);
        this.transientTimeWindow = transientTimeWindow;
    }

    public String getId() {
        return id;
    }

    public List<String> getFromNodeIds() {
        return fromNodeIds;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public RealDistribution getProbabilityDistribution() {
        return probabilityDistribution;
    }

    public double sampleProbability(double time) {
        if (probabilityDistribution != null) {
            return probabilityDistribution.cumulativeProbability(time);
        }
        return 1.0;
    }

    public RealDistribution getTimeToFailureDistribution() {
        return timeToFailureDistribution;
    }

    public double sampleTimeToFailure() {
        return timeToFailureDistribution.sample();
    }

    public boolean isConditionMet(List<Node> nodes) {
        return condition.test(nodes);
    }

    public Predicate<List<Node>> getCondition() {
        return condition;
    }

    public void setCondition(Predicate<List<Node>> condition) {
        this.condition = condition;
    }

    public boolean isTransientFaultValid(double eventTime, double currentTime) {
        if(this.transientTimeWindow != 0.0)
            return (eventTime - currentTime) <= transientTimeWindow;
        return true;
    }
}
