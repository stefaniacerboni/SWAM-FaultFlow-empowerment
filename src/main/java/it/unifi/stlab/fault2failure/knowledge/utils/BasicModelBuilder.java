package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Class that builds an example system composed of three Meta Components (Root_C, Leaf_B, Leaf_A).
 * It also gives an example on how to create failureModes, BooleanExpression and PropagationPorts.
 * Stores everything into the following attributes:
 * -metaComponents collects all the MetaComponents in the system
 * -failModes collects all the FailureModes that could happen inside the system
 * -failConnections collects all the PropagationPorts that describe the connections between faults and failures
 * as well as their ErrorMode, MetaComponents
 */
public class BasicModelBuilder {
    private static BasicModelBuilder singleInstance = new BasicModelBuilder();
    private static HashMap<String, FaultMode> faultModes;
    private static System system;

    private BasicModelBuilder() {
        faultModes = new HashMap<>();

        //Defines the System at Meta Level
        system = new System("S");
        MetaComponent a = new MetaComponent("Leaf_A");
        MetaComponent b = new MetaComponent("Leaf_B");
        MetaComponent c = new MetaComponent("Root_C");
        system.addComponent(a);
        system.addComponent(b);
        system.addComponent(c);
        system.setTopLevelComponent(c);
        CompositionPort abc = new CompositionPort(c);
        abc.addChild(a);
        abc.addChild(b);

        //Defines the Failure Modes and Fault Modes at Meta Level


        EndogenousFaultMode enFM_A1 = new EndogenousFaultMode("A_Fault1");
        EndogenousFaultMode enFM_A2 = new EndogenousFaultMode("A_Fault2");
        EndogenousFaultMode enFM_A3 = new EndogenousFaultMode("A_Fault3");
        faultModes.put(enFM_A1.getName(), enFM_A1);
        faultModes.put(enFM_A2.getName(), enFM_A2);
        faultModes.put(enFM_A3.getName(), enFM_A3);

        FailureMode a_failure1 = new FailureMode("A_Failure1");
        ErrorMode eM_A1 = new ErrorMode("A_Propagation1");
        eM_A1.addInputFaultMode(enFM_A1, enFM_A2, enFM_A3);
        eM_A1.addOutputFailureMode(a_failure1);
        eM_A1.setEnablingCondition("(A_Fault1)&&((A_Fault2) || (A_Fault3))", faultModes);
        eM_A1.setPDF("erlang(5,1)");

        EndogenousFaultMode enFM_A4 = new EndogenousFaultMode("A_Fault4");
        EndogenousFaultMode enFM_A5 = new EndogenousFaultMode("A_Fault5");
        faultModes.put(enFM_A4.getName(), enFM_A4);
        faultModes.put(enFM_A5.getName(), enFM_A5);

        FailureMode a_failure2 = new FailureMode("A_Failure2");
        ErrorMode eM_A2 = new ErrorMode("A_Propagation2");
        eM_A2.addInputFaultMode(enFM_A3, enFM_A4, enFM_A5);
        eM_A2.addOutputFailureMode(a_failure2);
        eM_A2.setEnablingCondition("(A_Fault3)&&((A_Fault4) || (A_Fault5))", faultModes);
        eM_A2.setPDF("erlang(7,1)");

        FailureMode a_failure3 = new FailureMode("A_Failure3");
        ErrorMode eM_A3 = new ErrorMode("A_Propagation3");
        eM_A3.addInputFaultMode(enFM_A2, enFM_A5);
        eM_A3.addOutputFailureMode(a_failure3);
        eM_A3.setEnablingCondition("A_Fault2 || A_Fault5", faultModes);
        eM_A3.setPDF("erlang(9,1)");

        a.addErrorMode(eM_A1,eM_A2,eM_A3);

        EndogenousFaultMode enFM_B1 = new EndogenousFaultMode("B_Fault1");
        EndogenousFaultMode enFM_B2 = new EndogenousFaultMode("B_Fault2");
        faultModes.put(enFM_B1.getName(), enFM_B1);
        faultModes.put(enFM_B2.getName(), enFM_B2);

        FailureMode b_failure1 = new FailureMode("B_Failure1");
        ErrorMode eM_B1 = new ErrorMode("B_Propagation1");
        eM_B1.addInputFaultMode(enFM_B1, enFM_B2);
        eM_B1.addOutputFailureMode(b_failure1);
        eM_B1.setEnablingCondition("B_Fault1 && B_Fault2", faultModes);
        eM_B1.setPDF("erlang(6,1)");

        EndogenousFaultMode enFM_B3 = new EndogenousFaultMode("B_Fault3");
        faultModes.put(enFM_B3.getName(), enFM_B3);

        FailureMode b_failure2 = new FailureMode("B_Failure2");
        ErrorMode eM_B2 = new ErrorMode("B_Propagation2");
        eM_B2.addInputFaultMode(enFM_B1, enFM_B3);
        eM_B2.addOutputFailureMode(b_failure2);
        eM_B2.setEnablingCondition("B_Fault1 && B_Fault3", faultModes);
        eM_B2.setPDF("erlang(2,1)");

        b.addErrorMode(eM_B1, eM_B2);


        ExogenousFaultMode exFM_C1 = new ExogenousFaultMode("C_Fault1");
        ExogenousFaultMode exFM_C2 = new ExogenousFaultMode("C_Fault2");
        ExogenousFaultMode exFM_C3 = new ExogenousFaultMode("C_Fault3");
        ExogenousFaultMode exFM_C4 = new ExogenousFaultMode("C_Fault4");
        ExogenousFaultMode exFM_C5 = new ExogenousFaultMode("C_Fault5");
        faultModes.put(exFM_C1.getName(), exFM_C1);
        faultModes.put(exFM_C2.getName(), exFM_C2);
        faultModes.put(exFM_C3.getName(), exFM_C3);
        faultModes.put(exFM_C4.getName(), exFM_C4);
        faultModes.put(exFM_C5.getName(), exFM_C5);

        a.addPropagationPort(
                new PropagationPort(a_failure1, exFM_C1, c),
                new PropagationPort(a_failure2, exFM_C2, c),
                new PropagationPort(a_failure3, exFM_C3, c));
        b.addPropagationPort(
                new PropagationPort(b_failure1, exFM_C4, c),
                new PropagationPort(b_failure2, exFM_C5, c));

        FailureMode c_failure1 = new FailureMode("C_Failure1");
        ErrorMode eM_C1 = new ErrorMode("C_Propagation1");
        eM_C1.addInputFaultMode(exFM_C2, exFM_C3);
        eM_C1.addOutputFailureMode(c_failure1);
        eM_C1.setEnablingCondition("C_Fault2 && C_Fault3", faultModes);
        eM_C1.setPDF("erlang(2,1)");

        EndogenousFaultMode enFM_C6 = new EndogenousFaultMode("C_Fault6");
        faultModes.put(enFM_C6.getName(), enFM_C6);

        FailureMode c_failure2 = new FailureMode("C_Failure2");
        ErrorMode eM_C2 = new ErrorMode("C_Propagation2");
        eM_C2.addInputFaultMode(exFM_C4, enFM_C6);
        eM_C2.addOutputFailureMode(c_failure2);
        eM_C2.setEnablingCondition("C_Fault6 && C_Fault4", faultModes);
        eM_C2.setPDF("erlang(2,1)");

        FailureMode c_failure3 = new FailureMode("C_Failure3");
        ErrorMode eM_C3 = new ErrorMode("C_Propagation3");
        eM_C3.addInputFaultMode(exFM_C5, enFM_C6);
        eM_C3.addOutputFailureMode(c_failure3);
        eM_C3.setEnablingCondition("C_Fault5 && C_Fault6", faultModes);
        eM_C3.setPDF("erlang(2,1)");

        c.addErrorMode(eM_C1,  eM_C2, eM_C3);
    }

    public static BasicModelBuilder getInstance() {
        if (singleInstance == null)
            singleInstance = new BasicModelBuilder();
        return singleInstance;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

    public System getSystem() {
        return system;
    }

    public static void createBaseDigitalTwin(Scenario scenario, System system, String serial){
        scenario.setSystem(system.getComponents().stream()
                .map(c -> new Component(c.getName() + serial, c))
                .collect(Collectors.toList()));
    }

    public static void injectFaultsIntoScenario(Scenario scenario, String serial){
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", faultModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", faultModes.get("C_Fault6"));

        Map<String, Component> current_system = scenario.getCurrentSystemMap();

        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), current_system.get("Leaf_A"+serial));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), current_system.get("Leaf_A"+serial));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), current_system.get("Leaf_A"+serial));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), current_system.get("Leaf_B"+serial));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), current_system.get("Leaf_B"+serial));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), current_system.get("Root_C"+serial));

    }

}
