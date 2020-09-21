package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScenarioTest {
    private Scenario scenario;

    @Before
    public void setup() {
        List<Component> current_system = new ArrayList<>();
        Component a1 = new Component("A_Serial1", BasicModelBuilder.getInstance().getMetaComponents().get("Leaf_A"));
        Component a2 = new Component("A_Serial2", BasicModelBuilder.getInstance().getMetaComponents().get("Leaf_A"));
        Component b1 = new Component("B_Serial1", BasicModelBuilder.getInstance().getMetaComponents().get("Leaf_B"));
        Component b2 = new Component("B_Serial2", BasicModelBuilder.getInstance().getMetaComponents().get("Leaf_B"));
        Component c = new Component("C_Serial", BasicModelBuilder.getInstance().getMetaComponents().get("Root_C"));
        current_system.add(a1);
        current_system.add(a2);
        current_system.add(b1);
        current_system.add(b2);
        current_system.add(c);

        scenario = new Scenario(current_system);
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
        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17));
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
        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17));
        //check if they're all there
        assertEquals(6, scenario.getIncomingFaults().size());
        //remove one
        scenario.removeFailure(A_fault1Occurred);
        //check if they're all there
        assertEquals(5, scenario.getIncomingFaults().size());
    }
}
