package it.unifi.stlab.fault2failure.operational;

import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
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
    public void setup(){
        BasicModelBuilder.build();
        List<Component> current_system = new ArrayList<>();
        Component a1 = new Component("A_Serial1", BasicModelBuilder.getMetaComponents().get("Leaf_A"));
        Component a2 = new Component("A_Serial2", BasicModelBuilder.getMetaComponents().get("Leaf_A"));
        Component b1 = new Component("B_Serial1", BasicModelBuilder.getMetaComponents().get("Leaf_B"));
        Component b2 = new Component("B_Serial2", BasicModelBuilder.getMetaComponents().get("Leaf_B"));
        Component c = new Component("C_Serial", BasicModelBuilder.getMetaComponents().get("Root_C"));
        current_system.add(a1);
        current_system.add(a2);
        current_system.add(b1);
        current_system.add(b2);
        current_system.add(c);

        scenario = new Scenario(BasicModelBuilder.getFailConnections(), current_system);
    }
    @Test
    public void testAddFailure(){
        //Begin with empty list
        assertEquals(0, scenario.getFailuresOccurred().size());

        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getFaultModes();
        //instantiate 6 occurrences
        Failure A_fault1Occurred = new Failure("A_fault1Occurred", faultModes.get("A_Fault1"));
        Failure A_fault2Occurred = new Failure("A_fault2Occurred", faultModes.get("A_Fault2"));
        Failure A_fault3Occurred = new Failure("A_fault3Occurred", faultModes.get("A_Fault3"));

        Failure B_fault1Occurred = new Failure("B_fault1Occurred", faultModes.get("B_Fault1"));
        Failure B_fault2Occurred = new Failure("B_fault2Occurred", faultModes.get("B_Fault2"));
        Failure C_fault6Occurred = new Failure("C_fault6Occurred", faultModes.get("C_Fault6"));
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
    public void testRemoveFailure(){
        //Begin with empty list
        assertEquals(0, scenario.getFailuresOccurred().size());

        HashMap<String, FaultMode> faultModes = BasicModelBuilder.getFaultModes();
        //instantiate 6 occurrences
        Failure A_fault1Occurred = new Failure("A_fault1Occurred", faultModes.get("A_Fault1"));
        Failure A_fault2Occurred = new Failure("A_fault2Occurred", faultModes.get("A_Fault2"));
        Failure A_fault3Occurred = new Failure("A_fault3Occurred", faultModes.get("A_Fault3"));

        Failure B_fault1Occurred = new Failure("B_fault1Occurred", faultModes.get("B_Fault1"));
        Failure B_fault2Occurred = new Failure("B_fault2Occurred", faultModes.get("B_Fault2"));
        Failure C_fault6Occurred = new Failure("C_fault6Occurred", faultModes.get("C_Fault6"));
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
