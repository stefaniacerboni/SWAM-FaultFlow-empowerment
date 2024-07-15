package it.unifi.stlab.stateflow;

public class Event {
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
