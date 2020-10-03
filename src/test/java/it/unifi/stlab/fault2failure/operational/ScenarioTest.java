package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScenarioTest {
    private Scenario scenario;
    private Map<String, Component> currentSystem;

    @Before
    public void setup() {
        System system = BasicModelBuilder.getInstance().getSystem();
        scenario = new Scenario(system);
        BasicModelBuilder.createBaseDigitalTwin(scenario, system, "_Test");
        currentSystem = scenario.getCurrentSystemMap();

    }

    @Test
    public void testAddFailure() {
        //Begin with empty list
        assertEquals(0, scenario.getFailuresOccurred().size());

        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getInstance().getFaultModes();
        //instantiate 6 occurrences
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"));
        //add 6 occurrences
        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), currentSystem.get("Leaf_B_Test"));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), currentSystem.get("Leaf_B_Test"));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), currentSystem.get("Root_C_Test"));
        //check if they're all there
        assertEquals(6, scenario.getIncomingFaults().size());
    }

    @Test
    public void testRemoveFailure() {
        //Begin with empty list
        assertEquals(0, scenario.getFailuresOccurred().size());

        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getInstance().getFaultModes();
        //instantiate 6 occurrences
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"));
        //add 6 occurrences
        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), currentSystem.get("Leaf_A_Test"));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), currentSystem.get("Leaf_B_Test"));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), currentSystem.get("Leaf_B_Test"));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), currentSystem.get("Root_C_Test"));
        //check if they're all there
        assertEquals(6, scenario.getIncomingFaults().size());
        //remove one
        scenario.removeFailure(A_fault1Occurred);
        //check if they're all there
        assertEquals(5, scenario.getIncomingFaults().size());
    }
}
