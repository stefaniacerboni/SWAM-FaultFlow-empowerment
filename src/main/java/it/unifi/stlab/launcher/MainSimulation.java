package it.unifi.stlab.launcher;

import it.unifi.stlab.faultflow.mapper.FaultFlowToStateFlowMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.launcher.systembuilder.SteamBoilerModelBuilder;
import it.unifi.stlab.stateflow.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainSimulation {
    public static void main(String[] args) throws InterruptedException {
        int numRuns = 100; // Number of simulation runs
        double simulationTime = 100.0; // Total simulation time
        double timestep = 1.0; // Time step for each simulation
        String rewardNodeId = "SystemFailure"; // Reward node ID
        Map<String, Double> faultInjections = new HashMap<>(); // Fault injections

        // Example fault injections
        faultInjections.put("Sensor1_MD", 1.0); // Inject Sensor1 Fault at time 1.0
        faultInjections.put("Sensor2_ED", 3.0); // Inject Sensor2 Fault at time 3.0
        faultInjections.put("Valve2_HF", 20.0); // Inject Valve1 Fault at time 3.0

        // Initialize simulation results container
        SimulationResult result = new SimulationResult();

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Run multiple simulations in parallel
        for (int i = 0; i < numRuns; i++) {
            //Instantiate model
            System s = SteamBoilerModelBuilder.getInstance().getSystem();
            FaultFlowToStateFlowMapper faultFlowToStateFlowMapper = new FaultFlowToStateFlowMapper();
            StateFlowModel stateFlowModel = faultFlowToStateFlowMapper.map(s);
            SimulationRunner runner = new SimulationRunner(stateFlowModel, simulationTime, faultInjections, timestep, rewardNodeId, result);
            executor.submit(runner);
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        // Calculate and print statistics
        java.lang.System.out.println("Mean: " + result.getMean());
        java.lang.System.out.println("Variance: " + result.getVariance());
        java.lang.System.out.println("95% Confidence Interval: " + result.getConfidenceInterval95());
    }

    private static Map<String, Node> initializeNodes() {
        // Initialize nodes here
        return new HashMap<>();
    }

    private static Map<String, StateTransition> initializeTransitions() {
        // Initialize state transitions here
        return new HashMap<>();
    }
}
