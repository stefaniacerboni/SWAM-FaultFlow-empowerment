package it.unifi.stlab.stateflow;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {
    private final List<Double> results;

    public SimulationResult() {
        this.results = new ArrayList<>();
    }

    public synchronized void addResult(double result) {
        System.out.println("Adding result: " + result); // Debug print
        results.add(result);
    }

    public List<Double> getResults() {
        return results;
    }

    public double getMean() {
        return results.stream().mapToDouble(val -> val).average().orElse(0.0);
    }

    public double getVariance() {
        double mean = getMean();
        return results.stream().mapToDouble(val -> Math.pow(val - mean, 2)).average().orElse(0.0);
    }

    public double getConfidenceInterval95() {
        double mean = getMean();
        double stdDev = Math.sqrt(getVariance());
        return 1.96 * stdDev / Math.sqrt(results.size()); // 95% confidence interval
    }
}

