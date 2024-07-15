package it.unifi.stlab.launcher;

import it.unifi.stlab.faultflow.mapper.FaultFlowToStateFlowMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.launcher.systembuilder.SteamBoilerModelBuilder;
import it.unifi.stlab.stateflow.StateFlowModel;

import java.util.HashMap;
import java.util.Map;


public class FaultFlowToStateFlow {
    public static void main(String[] args) {
        System s = SteamBoilerModelBuilder.getInstance().getSystem();
        FaultFlowToStateFlowMapper faultFlowToStateFlowMapper = new FaultFlowToStateFlowMapper();
        StateFlowModel stateFlowModel = faultFlowToStateFlowMapper.map(s);
        Map<String, Double> faultInjections = new HashMap<>();
        faultInjections.put("Sensor1_MD", 1.0); // Inject Sensor1 Fault at time 1.0
        faultInjections.put("Sensor2_ED", 3.0); // Inject Sensor2 Fault at time 3.0
        faultInjections.put("Valve2_HF", 20.0); // Inject Valve1 Fault at time 3.0
        stateFlowModel.simulateFlow(40.0, faultInjections, 1.0, "SystemFailure");
        stateFlowModel.printNodeStates();

    }
}
