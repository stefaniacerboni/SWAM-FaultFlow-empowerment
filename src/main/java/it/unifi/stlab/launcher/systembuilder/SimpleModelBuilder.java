package it.unifi.stlab.launcher.systembuilder;

import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.*;

import java.util.HashMap;

public class SimpleModelBuilder {

    private static SimpleModelBuilder single_instance;
    private static System system;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, FailureMode> failureModes;
    private static HashMap<String, ErrorMode> errorModes;

    private SimpleModelBuilder() {
        faultModes = new HashMap<>();
        failureModes = new HashMap<>();
        errorModes = new HashMap<>();

        // Definizione composizione del sistema

        system = new System("S");
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

        EndogenousFaultMode enFM_B1 = new EndogenousFaultMode("B_Fault1");
        enFM_B1.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_B2 = new EndogenousFaultMode("B_Fault2");
        enFM_B2.setArisingPDF("gaussian(10,5)");
        EndogenousFaultMode enFM_C4 = new EndogenousFaultMode("C_Fault4");
        enFM_C4.setArisingPDF("uniform(20,40)");

        faultModes.put(enFM_B1.getName(), enFM_B1);
        faultModes.put(enFM_B2.getName(), enFM_B2);
        faultModes.put(enFM_C4.getName(), enFM_C4);

        // Definizione di Fault Mode Esogeni

        ExogenousFaultMode exFM_C3 = new ExogenousFaultMode("C_Fault3");
        faultModes.put(exFM_C3.getName(), exFM_C3);

        // Definizione delle Failure Mode per B

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

        b.addPropagationPort(
                new PropagationPort(fM_B1, exFM_C3, c)
        );

        // Definizione delle Failure Mode per C

        FailureMode fM_C2 = new FailureMode("C_Failure2");
        ErrorMode eM_C2 = new ErrorMode("C_ToFailure2");
        eM_C2.addInputFaultMode(exFM_C3, enFM_C4);
        eM_C2.addOutputFailureMode(fM_C2);
        eM_C2.setEnablingCondition("C_Fault3 && C_Fault4", faultModes);
        eM_C2.setPDF("dirac(0)");
        errorModes.put(eM_C2.getName(), eM_C2);
        failureModes.put(fM_C2.getDescription(), fM_C2);

        c.addErrorMode(eM_C2);
    }

    public static SimpleModelBuilder getInstance() {
        if (single_instance == null)
            single_instance = new SimpleModelBuilder();
        return single_instance;
    }

    public System getSystem() {
        return system;
    }

    public HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

}
