package it.unifi.stlab.stateflow;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StateFlowModel {
    private Map<String, Node> nodes;
    private Map<String, StateTransition> transitions;
    private PriorityQueue<Event> eventQueue;
    private double currentTime;
    private ScheduledExecutorService scheduler;
    private double timeStep;
    private String rewardNodeId;

    public StateFlowModel(Map<String, Node> nodes, Map<String, StateTransition> transitions) {
        this.nodes = nodes;
        this.transitions = transitions;
        this.eventQueue = new PriorityQueue<>(Comparator.comparingDouble(Event::getScheduledTime));
        this.currentTime = 0;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void simulateFlow(double simulationTime, Map<String, Double> faultInjections, double timeStep, String rewardNodeId) {
        this.timeStep = timeStep;
        this.rewardNodeId = rewardNodeId;
        initializeFaultInjections(faultInjections);
        //startBackgroundRoutine();
        while (!eventQueue.isEmpty() && currentTime <= simulationTime) {
            Event event = eventQueue.poll();
            currentTime = event.getScheduledTime();
            //rejuvenateNodes();
            processEvent(event);
            if (isRewardNodeActive()) {
                break;
            }
        }
        scheduler.shutdownNow();
    }

    private void initializeFaultInjections(Map<String, Double> faultInjections) {
        for (Map.Entry<String, Double> entry : faultInjections.entrySet()) {
            String nodeId = entry.getKey();
            double time = entry.getValue();
            Node occurrenceNode = nodes.get(nodeId + "_Occurrence");
            if (occurrenceNode != null) {
                eventQueue.add(new Event(time, transitions.get(nodeId + "_Transition")));
            }
        }
    }

    private void startBackgroundRoutine() {
        Runnable checkTransitions = () -> {
            for (StateTransition transition : transitions.values()) {
                if (transition.getId().endsWith("_Transition")) {
                    List<Node> relevantNodes = getRelevantNodes(transition.getFromNodeIds());
                    if (transition.isConditionMet(relevantNodes)) {
                        double eventTime = currentTime + transition.sampleTimeToFailure();
                        if (transition.isTransientFaultValid(eventTime, currentTime)) {
                            scheduleTransitionEvent(transition, currentTime);
                        }
                    }
                }
            }
        };
        scheduler.scheduleAtFixedRate(checkTransitions, 0, (long) (timeStep * 1000), TimeUnit.MILLISECONDS);
    }

    private List<Node> getRelevantNodes(List<String> nodeIds) {
        return nodeIds.stream()
                .map(nodes::get)
                .collect(Collectors.toList());
    }

    private void scheduleTransitionEvent(StateTransition transition, double startTime) {
        if(transition.getTimeToFailureDistribution() != null){
            //The it's an errorMode, we have to sample timetofailure:
            double eventTime = startTime + transition.sampleTimeToFailure();
            eventQueue.add(new Event(eventTime, transition));
        }
        else {
            double probability = transition.sampleProbability(currentTime - startTime);
            System.out.println("Checking probability of: "+transition.getId());
            if (Math.random() <= probability) {
                System.out.println(transition.getId()+" Occurred, scheduling-");
                eventQueue.add(new Event(startTime, transition));
            }
        }
    }

    private void processEvent(Event event) {
        StateTransition transition = event.getTransition();
        List<Node> relevantNodes = getRelevantNodes(transition.getFromNodeIds());

        if (transition.isConditionMet(relevantNodes)) {
            Node toNode = nodes.get(transition.getToNodeId());
            // Perform the state transition
            transitionState(relevantNodes, toNode);
            // Schedule new events based on the resulting state
            for (StateTransition nextTransition : transitions.values()) {
                if (nextTransition.getFromNodeIds().contains(transition.getToNodeId())
                        && nextTransition.isConditionMet(getRelevantNodes(nextTransition.getFromNodeIds()))) {
                    scheduleTransitionEvent(nextTransition, currentTime);
                }
            }
        }
    }

    private boolean isRewardNodeActive() {
        Node rewardNode = nodes.get(rewardNodeId);
        return rewardNode != null && (Boolean) rewardNode.getState("active");
    }

    private void rejuvenateNodes() {
        for (Node node : nodes.values()) {
            node.rejuvenate(currentTime);
        }
    }

    private void transitionState(List<Node> fromNodes, Node toNode) {
        /*
        for (Node fromNode : fromNodes) {
            fromNode.removeTokens(1);
            fromNode.setState("active", false);
        }

         */
        toNode.addTokens(1);
        toNode.setState("active", true);
    }

    public void printNodeStates() {
        System.out.println("Node States at time " + currentTime + ":");
        for (Node node : nodes.values()) {
            System.out.println("Node ID: " + node.getId() + ", Tokens: " + node.getTokens() + ", States: " + node.getStates());
        }
    }

    public double getCurrentTime() {
        return currentTime;
    }

    private class Event {
        private double scheduledTime;
        private StateTransition transition;

        public Event(double scheduledTime, StateTransition transition) {
            this.scheduledTime = scheduledTime;
            this.transition = transition;
        }

        public double getScheduledTime() {
            return scheduledTime;
        }

        public StateTransition getTransition() {
            return transition;
        }
    }
}
