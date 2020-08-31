package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NewModelBuilder {
    private static System system;
    private static HashMap<String, FaultMode> faultModes;


    public static System getSystem() {
        return system;
    }

    public static Map<String, MetaComponent> getMetaComponents() {
        return system.getComponents().stream().collect(Collectors.toMap(MetaComponent::getName, Function.identity()));
    }

    public static HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }


    public static void build() {
        faultModes= new HashMap<>();

        system = new System("S");
        MetaComponent a = new MetaComponent("A");
        MetaComponent b = new MetaComponent("B");
        MetaComponent c = new MetaComponent("C");
        system.addComponent(a,b,c);
        system.setTopLevelComponent(c);
        CompositionPort abc = new CompositionPort(c);
        abc.addChild(a);
        abc.addChild(b);

        EndogenousFaultMode enFM_A1 = new EndogenousFaultMode("A_Fault1");
        enFM_A1.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_A2 = new EndogenousFaultMode("A_Fault2");
        enFM_A2.setArisingPDF("uniform(5,10)");
        EndogenousFaultMode enFM_A3 = new EndogenousFaultMode("A_Fault3");
        enFM_A3.setArisingPDF("exp(10)");

        faultModes.put(enFM_A1.getName(), enFM_A1);
        faultModes.put(enFM_A2.getName(), enFM_A2);
        faultModes.put(enFM_A3.getName(), enFM_A3);

        FailureMode fM_A1 = new FailureMode("A_Failure1");
        ErrorMode eM_A1 = new ErrorMode("A_ToFailure1");
        eM_A1.addInputFaultMode(enFM_A1, enFM_A2, enFM_A3);
        eM_A1.addOutputFailureMode(fM_A1);
        eM_A1.setEnablingCondition("A_Fault1 && (A_Fault2 || A_Fault3)", faultModes);
        eM_A1.setPDF("erlang(1,5)");

        FailureMode fM_A2 = new FailureMode("A_Failure2");
        ErrorMode eM_A2 = new ErrorMode("A_ToFailure2");
        eM_A2.addInputFaultMode(enFM_A1, enFM_A3);
        eM_A2.addOutputFailureMode(fM_A2);
        eM_A2.setEnablingCondition("A_Fault2 && A_Fault3", faultModes);
        eM_A2.setPDF("exp(5)");

        a.addErrorMode(eM_A1, eM_A2);

        EndogenousFaultMode enFM_B1 = new EndogenousFaultMode("B_Fault1");
        enFM_B1.setArisingPDF("exp(3)");
        EndogenousFaultMode enFM_B2 = new EndogenousFaultMode("B_Fault2");
        enFM_B2.setArisingPDF("erlang(10,5)");

        faultModes.put(enFM_B1.getName(), enFM_B1);
        faultModes.put(enFM_B2.getName(), enFM_B2);

        FailureMode fM_B1 = new FailureMode("B_Failure1");
        ErrorMode eM_B1 = new ErrorMode("B_ToFailure1");
        eM_B1.addInputFaultMode(enFM_B1, enFM_B2);
        eM_B1.addOutputFailureMode(fM_B1);
        eM_B1.setEnablingCondition("B_Fault1 && B_Fault2", faultModes);
        eM_B1.setPDF("exp(3)");

        b.addErrorMode(eM_B1);



        ExogenousFaultMode exFM_C1 = new ExogenousFaultMode("C_Fault1");
        ExogenousFaultMode exFM_C2 = new ExogenousFaultMode("C_Fault2");
        ExogenousFaultMode exFM_C3 = new ExogenousFaultMode("C_Fault3");

        a.addPropagationPort(
                new PropagationPort(fM_A1, exFM_C1, c),
                new PropagationPort(fM_A2, exFM_C2, c));
        b.addPropagationPort(new PropagationPort(fM_B1, exFM_C3, c));

        FailureMode fM_C1 = new FailureMode("C_Failure1");
        ErrorMode eM_C1 = new ErrorMode("C_ToFailure1");
        eM_C1.addInputFaultMode(exFM_C1, exFM_C2);
        eM_C1.addOutputFailureMode(fM_C1);
        eM_C1.setEnablingCondition("C_Fault1 && C_Fault2", faultModes);
        eM_C1.setPDF("exp(3)");

        EndogenousFaultMode enFM_C4 = new EndogenousFaultMode("C_Fault4");
        enFM_C4.setArisingPDF("uniform(20,40)");
        faultModes.put(enFM_C4.getName(), enFM_C4);

        FailureMode fM_C2 = new FailureMode("C_Failure2");
        ErrorMode eM_C2 = new ErrorMode("C_ToFailure2");
        eM_C2.addInputFaultMode(exFM_C3, enFM_C4);
        eM_C2.addOutputFailureMode(fM_C2);
        eM_C2.setEnablingCondition("C_Fault3 && C_Fault4", faultModes);
        eM_C2.setPDF("exp(3)");

        c.addErrorMode(eM_C1, eM_C2);
    }
}
