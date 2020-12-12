package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DecemberModelBuilder {
    private static DecemberModelBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;

    private DecemberModelBuilder() {
        faultModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("S");
        MetaComponent a = new MetaComponent("A");
        MetaComponent b = new MetaComponent("B");
        MetaComponent c = new MetaComponent("C");
        system.addComponent(a, b, c);
        system.setTopLevelComponent(c);
        CompositionPort abc = new CompositionPort(c);
        abc.addChild(a);
        abc.addChild(b);

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
        enFM_B2.setArisingPDF("erlang(10,5)");
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

        FailureMode fM_A2 = new FailureMode("A_Failure2");
        ErrorMode eM_A2 = new ErrorMode("A_ToFailure2");
        eM_A2.addInputFaultMode(enFM_A4, enFM_A5);
        eM_A2.addOutputFailureMode(fM_A2);
        eM_A2.setEnablingCondition("A_Fault4 && A_Fault5", faultModes);
        eM_A2.setPDF("exp(5)");

        a.addErrorMode(eM_A1, eM_A2);

        FailureMode fM_B1 = new FailureMode("B_Failure1");
        ErrorMode eM_B1 = new ErrorMode("B_ToFailure1");
        eM_B1.addInputFaultMode(enFM_B1, enFM_B2);
        eM_B1.addOutputFailureMode(fM_B1);
        eM_B1.setEnablingCondition("B_Fault1 && B_Fault2", faultModes);
        eM_B1.setPDF("exp(3)");

        b.addErrorMode(eM_B1);

        // Definizione delle propagation port

        a.addPropagationPort(
                new PropagationPort(fM_A1, exFM_C1, c),
                new PropagationPort(fM_A2, exFM_C2, c));
        b.addPropagationPort(
                new PropagationPort(fM_B1, exFM_A2, a),
                new PropagationPort(fM_B1, exFM_C3, c)
                );

        // Definizione delle Failure Mode per C

        FailureMode fM_C1 = new FailureMode("C_Failure1");
        ErrorMode eM_C1 = new ErrorMode("C_ToFailure1");
        eM_C1.addInputFaultMode(exFM_C1, exFM_C2);
        eM_C1.addOutputFailureMode(fM_C1);
        eM_C1.setEnablingCondition("C_Fault1 && C_Fault2", faultModes);
        eM_C1.setPDF("dirac(0)");

        FailureMode fM_C2 = new FailureMode("C_Failure2");
        ErrorMode eM_C2 = new ErrorMode("C_ToFailure2");
        eM_C2.addInputFaultMode(exFM_C3, enFM_C4);
        eM_C2.addOutputFailureMode(fM_C2);
        eM_C2.setEnablingCondition("C_Fault3 && C_Fault4", faultModes);
        eM_C2.setPDF("dirac(0)");

        c.addErrorMode(eM_C1, eM_C2);
    }

    public static DecemberModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new DecemberModelBuilder();
        return single_instance;
    }

    public Map<String, MetaComponent> getMetaComponents() {
        return system.getComponents().stream().collect(Collectors.toMap(MetaComponent::getName, Function.identity()));
    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

    //Base Level Methods: Create a Scenario, Sets its Base System and Injects faults into that

    public static void createBaseDigitalTwin(Scenario scenario, System system, String serial){
        scenario.setSystem(system.getComponents().stream()
                .map(c -> new Component(c.getName() + serial, c))
                .collect(Collectors.toList()));
    }

    public static void injectFaultsIntoScenario(Scenario scenario, String serial){
        Fault A_fault1Occurred = new Fault("A_fault1Occurred", faultModes.get("A_Fault1"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", faultModes.get("A_Fault3"));
        Fault A_fault4Occurred = new Fault("A_fault4Occurred", faultModes.get("A_Fault4"));
        Fault A_fault5Occurred = new Fault("A_fault5Occurred", faultModes.get("A_Fault5"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", faultModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", faultModes.get("B_Fault2"));
        Fault C_fault4Occurred = new Fault("C_fault4Occurred", faultModes.get("C_Fault4"));

        Map<String, Component> currentSystem = scenario.getCurrentSystemMap();
        scenario.addFault(A_fault1Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)A_fault1Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("A"+serial));
        scenario.addFault(A_fault3Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)A_fault3Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("A"+serial));
        scenario.addFault(A_fault4Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)A_fault4Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("A"+serial));
        scenario.addFault(A_fault5Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)A_fault5Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("A"+serial));

        scenario.addFault(B_fault1Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)B_fault1Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("B"+serial));
        scenario.addFault(B_fault2Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)B_fault2Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("B"+serial));

        scenario.addFault(C_fault4Occurred,
                          BigDecimal.valueOf(
                                  SampleGenerator.generate(((EndogenousFaultMode)C_fault4Occurred.getFaultMode()).getArisingPDFToString())),
                          currentSystem.get("C"+serial));
    }


}
