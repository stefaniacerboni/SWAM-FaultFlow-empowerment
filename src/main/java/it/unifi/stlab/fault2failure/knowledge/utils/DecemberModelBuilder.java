package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.operational.Error;
import it.unifi.stlab.fault2failure.operational.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DecemberModelBuilder {
    private static DecemberModelBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, FailureMode> failureModes;
    private static HashMap<String, ErrorMode> errorModes;

    private DecemberModelBuilder() {
        faultModes = new HashMap<>();
        failureModes = new HashMap<>();
        errorModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("S_System", "sys_manufacturer", "sys_model");
        Component a = new Component("A");
        Component b = new Component("B");
        Component c = new Component("C");
        Component s = new Component("S");
        system.addComponent(a, b, c, s);
        system.setTopLevelComponent(s);
        CompositionPort ac = new CompositionPort(a, c);
        CompositionPort bc = new CompositionPort(b, c);
        c.addCompositionPorts(ac, bc);
        CompositionPort cs = new CompositionPort(c, s);
        s.addCompositionPorts(cs);

        // Definizione di Fault Mode Endogeni

        EndogenousFaultMode enFM_A1 = new EndogenousFaultMode("A_Fault1");
        enFM_A1.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_A3 = new EndogenousFaultMode("A_Fault3");
        enFM_A3.setArisingPDF("exp(10)");
        EndogenousFaultMode enFM_A4 = new EndogenousFaultMode("A_Fault4");
        enFM_A4.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_A5 = new EndogenousFaultMode("A_Fault5");
        enFM_A5.setArisingPDF("exp(10)");
        EndogenousFaultMode enFM_B1 = new EndogenousFaultMode("B_Fault1");
        enFM_B1.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_B2 = new EndogenousFaultMode("B_Fault2");
        enFM_B2.setArisingPDF("gaussian(10,5)");
        EndogenousFaultMode enFM_C4 = new EndogenousFaultMode("C_Fault4");
        enFM_C4.setArisingPDF("uniform(20,40)");

        faultModes.put(enFM_A1.getName(), enFM_A1);
        faultModes.put(enFM_A3.getName(), enFM_A3);
        faultModes.put(enFM_A4.getName(), enFM_A4);
        faultModes.put(enFM_A5.getName(), enFM_A5);
        faultModes.put(enFM_B1.getName(), enFM_B1);
        faultModes.put(enFM_B2.getName(), enFM_B2);
        faultModes.put(enFM_C4.getName(), enFM_C4);

        // Definizione di Fault Mode Esogeni

        ExogenousFaultMode exFM_A2 = new ExogenousFaultMode("A_Fault2");
        ExogenousFaultMode exFM_C1 = new ExogenousFaultMode("C_Fault1");
        ExogenousFaultMode exFM_C2 = new ExogenousFaultMode("C_Fault2");
        ExogenousFaultMode exFM_C3 = new ExogenousFaultMode("C_Fault3");

        faultModes.put(exFM_A2.getName(), exFM_A2);
        faultModes.put(exFM_C1.getName(), exFM_C1);
        faultModes.put(exFM_C2.getName(), exFM_C2);
        faultModes.put(exFM_C3.getName(), exFM_C3);

        // Definizione delle Failure Mode per A e B

        FailureMode fM_A1 = new FailureMode("A_Failure1");
        ErrorMode eM_A1 = new ErrorMode("A_ToFailure1");
        eM_A1.addInputFaultMode(enFM_A1, exFM_A2, enFM_A3);
        eM_A1.addOutputFailureMode(fM_A1);
        eM_A1.setEnablingCondition("A_Fault1 && (A_Fault2 || A_Fault3)", faultModes);
        eM_A1.setPDF("erlang(1,5)");
        errorModes.put(eM_A1.getName(), eM_A1);
        failureModes.put(fM_A1.getDescription(), fM_A1);

        FailureMode fM_A2 = new FailureMode("A_Failure2");
        ErrorMode eM_A2 = new ErrorMode("A_ToFailure2");
        eM_A2.addInputFaultMode(enFM_A4, enFM_A5);
        eM_A2.addOutputFailureMode(fM_A2);
        eM_A2.setEnablingCondition("A_Fault4 && A_Fault5", faultModes);
        eM_A2.setPDF("exp(5)");
        errorModes.put(eM_A2.getName(), eM_A2);
        failureModes.put(fM_A2.getDescription(), fM_A2);


        a.addErrorMode(eM_A1, eM_A2);

        FailureMode fM_B1 = new FailureMode("B_Failure1");
        ErrorMode eM_B1 = new ErrorMode("B_ToFailure1");
        eM_B1.addInputFaultMode(enFM_B1, enFM_B2);
        eM_B1.addOutputFailureMode(fM_B1);
        eM_B1.setEnablingCondition("B_Fault1 && B_Fault2", faultModes);
        eM_B1.setPDF("exp(3)");
        errorModes.put(eM_B1.getName(), eM_B1);
        failureModes.put(fM_B1.getDescription(), fM_B1);


        b.addErrorMode(eM_B1);

        // Definizione delle propagation port

        a.addPropagationPort(
                new PropagationPort(fM_A1, exFM_C1, c),
                new PropagationPort(fM_A2, exFM_C2, c));
        b.addPropagationPort(
                new PropagationPort(fM_B1, exFM_A2, a),
                new PropagationPort(fM_B1, exFM_C3, c, BigDecimal.valueOf(0.6))
        );

        // Definizione delle Failure Mode per C

        FailureMode fM_C1 = new FailureMode("C_Failure1");
        ErrorMode eM_C1 = new ErrorMode("C_ToFailure1");
        eM_C1.addInputFaultMode(exFM_C1, exFM_C2);
        eM_C1.addOutputFailureMode(fM_C1);
        eM_C1.setEnablingCondition("C_Fault1 && C_Fault2", faultModes);
        eM_C1.setPDF("dirac(0)");
        errorModes.put(eM_C1.getName(), eM_C1);
        failureModes.put(fM_C1.getDescription(), fM_C1);


        FailureMode fM_C2 = new FailureMode("C_Failure2");
        ErrorMode eM_C2 = new ErrorMode("C_ToFailure2");
        eM_C2.addInputFaultMode(exFM_C3, enFM_C4);
        eM_C2.addOutputFailureMode(fM_C2);
        eM_C2.setEnablingCondition("C_Fault3 && C_Fault4", faultModes);
        eM_C2.setPDF("dirac(0)");
        errorModes.put(eM_C2.getName(), eM_C2);
        failureModes.put(fM_C2.getDescription(), fM_C2);


        c.addErrorMode(eM_C1, eM_C2);
    }

    public static DecemberModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new DecemberModelBuilder();
        return single_instance;
    }

    public static void createBaseDigitalTwin(Scenario scenario, System system, String serial) {
        scenario.setSystem(system.getComponents().stream()
                .map(c -> new ConcreteComponent(c.getName() + serial, c))
                .collect(Collectors.toList()));
    }

    public static void injectFaultsIntoScenario(Scenario scenario, String serial) {
        //Tempi di occorrenza specificati formalmente
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"), BigDecimal.valueOf(10.0));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"), BigDecimal.valueOf(12.0));
        Fault A_fault4Occurred = new Fault("A_fault4Occurred", faultModes.get("A_Fault4"), BigDecimal.valueOf(13.0));
        Fault A_fault5Occurred = new Fault("A_fault5Occurred", faultModes.get("A_Fault5"), BigDecimal.valueOf(2.0));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"), BigDecimal.valueOf(20.0));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"), BigDecimal.valueOf(20.0));
        Fault C_fault4Occurred = new Fault("C_fault4Occurred", faultModes.get("C_Fault4"), BigDecimal.valueOf(30.0));

        Failure A_Failure2 = new Failure("A_failure2Occurred", failureModes.get("A_Failure2"), BigDecimal.TEN);
        Error errorModeA = new it.unifi.stlab.fault2failure.operational.Error("errorModeA", errorModes.get("A_ToFailure1"), BigDecimal.TEN);
        Fault C_Fault3 = new Fault("C_Fault3", faultModes.get("C_Fault3"), BigDecimal.TEN);
        Fault C_Fault2 = new Fault("C_Fault2", faultModes.get("C_Fault2"), BigDecimal.ONE);

        Map<String, ConcreteComponent> currentSystem = scenario.getCurrentSystemMap();

        scenario.addEvent(A_fault1Occurred, currentSystem.get("A" + serial));
        scenario.addEvent(A_fault3Occurred, currentSystem.get("A" + serial));
        scenario.addEvent(A_fault4Occurred, currentSystem.get("A" + serial));
        scenario.addEvent(A_fault5Occurred, currentSystem.get("A" + serial));
        scenario.addEvent(B_fault1Occurred, currentSystem.get("B" + serial));
        scenario.addEvent(B_fault2Occurred, currentSystem.get("B" + serial));
        scenario.addEvent(C_fault4Occurred, currentSystem.get("C" + serial));

        //ADD Failure e error
        scenario.addEvent(A_Failure2, currentSystem.get("A" + serial));
        scenario.addCustomErrorDelay(errorModeA);

        //ADD Exo fault
        scenario.addEvent(C_Fault3, currentSystem.get("C" + serial));
        scenario.addEvent(C_Fault2, currentSystem.get("C" + serial));

    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

    public static HashMap<String, FailureMode> getFailureModes() {
        return failureModes;
    }
}
