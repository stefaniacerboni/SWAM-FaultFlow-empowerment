package it.unifi.stlab.stateflow;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String id;
    private Map<String, Object> states; // Store different states
    private int tokens;
    private double rejuvenationInterval; // Interval for rejuvenation
    private double lastRejuvenationTime; // Last time the node was rejuvenated


    public Node(String id, int tokens) {
        this.id = id;
        this.tokens = tokens;
        this.states = new HashMap<>();
        this.rejuvenationInterval = 0;
        this.lastRejuvenationTime = 0;
    }

    public Node(String id, int tokens, double rejuvenationInterval) {
        this(id, tokens);
        this.rejuvenationInterval = rejuvenationInterval;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getStates() {
        return states;
    }

    public void setStates(Map<String, Object> states) {
        this.states = states;
    }

    public Object getState(String key) {
        return states.get(key);
    }

    public void setState(String key, Object value) {
        states.put(key, value);
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void addTokens(int tokens) {
        this.tokens += tokens;
    }

    public void removeTokens(int tokens) {
        this.tokens -= tokens;
    }

    public void rejuvenate(double currentTime) {
        if (currentTime - lastRejuvenationTime >= rejuvenationInterval) {
            // Reset the state and tokens as needed
            this.tokens = 0;
            this.states.put("active", false);
            lastRejuvenationTime = currentTime;
        }
    }
}
