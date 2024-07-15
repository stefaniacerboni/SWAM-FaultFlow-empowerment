package it.unifi.stlab.stateflow;

import java.util.Map;

public class SimulationRunner implements Runnable {
    private final StateFlowModel stateFlowModel;
    private final double simulationTime;
    private final Map<String, Double> faultInjections;
    private final double timestep;
    private final String rewardNodeId;
    private final SimulationResult result;

    public SimulationRunner(StateFlowModel stateFlowModel, double simulationTime, Map<String, Double> faultInjections, double timestep, String rewardNodeId, SimulationResult result) {
        this.stateFlowModel = stateFlowModel;
        this.simulationTime = simulationTime;
        this.faultInjections = faultInjections;
        this.timestep = timestep;
        this.rewardNodeId = rewardNodeId;
        this.result = result;
    }

    @Override
    public void run() {
        stateFlowModel.simulateFlow(simulationTime, faultInjections, timestep, rewardNodeId);
        double finalTime = stateFlowModel.getCurrentTime(); // Example result collection, adjust as needed
        System.out.println("Simulation completed. Final time: " + finalTime);
        result.addResult(finalTime);
    }
}
