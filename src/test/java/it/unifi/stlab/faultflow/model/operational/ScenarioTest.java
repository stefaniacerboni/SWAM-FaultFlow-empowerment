package it.unifi.stlab.faultflow.model.operational;

import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.translator.BasicModelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScenarioTest {
    private Scenario scenario;
    private Map<String, ConcreteComponent> currentSystem;

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
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"), BigDecimal.valueOf(10));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"), BigDecimal.valueOf(13));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"), BigDecimal.valueOf(16));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"), BigDecimal.valueOf(12));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"), BigDecimal.valueOf(18));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"), BigDecimal.valueOf(17));
        //add 6 occurrences
        scenario.addEvent(A_fault1Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(A_fault2Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(A_fault3Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(B_fault1Occurred, currentSystem.get("Leaf_B_Test"));
        scenario.addEvent(B_fault2Occurred, currentSystem.get("Leaf_B_Test"));
        scenario.addEvent(C_fault6Occurred, currentSystem.get("Root_C_Test"));
        //check if they're all there
        assertEquals(6, scenario.getIncomingEvents().size());
    }

    @Test
    public void testRemoveFailure() {
        //Begin with empty list
        assertEquals(0, scenario.getFailuresOccurred().size());

        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getInstance().getFaultModes();
        //instantiate 6 occurrences
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"), BigDecimal.valueOf(10));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"), BigDecimal.valueOf(13));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"), BigDecimal.valueOf(16));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"), BigDecimal.valueOf(12));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"), BigDecimal.valueOf(18));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"), BigDecimal.valueOf(17));
        //add 6 occurrences
        scenario.addEvent(A_fault1Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(A_fault2Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(A_fault3Occurred, currentSystem.get("Leaf_A_Test"));
        scenario.addEvent(B_fault1Occurred, currentSystem.get("Leaf_B_Test"));
        scenario.addEvent(B_fault2Occurred, currentSystem.get("Leaf_B_Test"));
        scenario.addEvent(C_fault6Occurred, currentSystem.get("Root_C_Test"));
        //check if they're all there
        assertEquals(6, scenario.getIncomingEvents().size());
        //remove one
        scenario.removeEvent(A_fault1Occurred);
        //check if they're all there
        assertEquals(5, scenario.getIncomingEvents().size());
    }
}
