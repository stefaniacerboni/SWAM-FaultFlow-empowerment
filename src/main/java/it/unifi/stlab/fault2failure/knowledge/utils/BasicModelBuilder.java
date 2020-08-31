package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class that builds an example system composed of three Meta Components (Root_C, Leaf_B, Leaf_A).
 * It also gives an example on how to create failureModes, BooleanExpression and PropagationPorts.
 * Stores everything into the following attributes:
 * -metaComponents collects all the MetaComponents in the system
 * -failModes collects all the FailureModes that could happen inside the system
 * -failConnections collects all the PropagationPorts that describe the connections between faults and failures
 *                   as well as their ErrorMode, MetaComponents
 */
public class BasicModelBuilder{
    private static HashMap<String, MetaComponent> metaComponents;
    private static HashMap<String, FaultMode> faultModes;
    private static HashMap<String, List<PropagationPort>> failConnections;
    private static System system;

    public static HashMap<String, MetaComponent> getMetaComponents() {
        return metaComponents;
    }

    public static HashMap<String, FaultMode> getFaultModes() {
        return faultModes;
    }

    public static HashMap<String, List<PropagationPort>> getFailConnections() {
        return failConnections;
    }

    public static System getSystem() {
        return system;
    }

    public static void build() {
        metaComponents = new HashMap<>();
        faultModes = new HashMap<>();
        failConnections = new HashMap<>();
        /*
        metaComponents.put("Root_C", new MetaComponent("Root_C"));
        CompositionPort abc = new CompositionPort(metaComponents.get("Root_C"));
        metaComponents.put("Leaf_A", new MetaComponent("Leaf_A"));
        metaComponents.put("Leaf_B", new MetaComponent("Leaf_B"));
        abc.addChild(metaComponents.get("Leaf_A"));
        abc.addChild(metaComponents.get("Leaf_B"));
         */
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

        metaComponents.put("Root_C", c);
        metaComponents.put("Leaf_A", a);
        metaComponents.put("Leaf_B", b);


        FailureMode a_failure1 = new FailureMode("A_Failure1");
        FailureMode a_failure2 = new FailureMode("A_Failure2");
        FailureMode a_failure3 = new FailureMode("A_Failure3");

        FailureMode b_failure1 = new FailureMode("B_Failure1");
        FailureMode b_failure2 = new FailureMode("B_Failure2");

        FailureMode c_failure1 = new FailureMode("C_Failure1");
        FailureMode c_failure2 = new FailureMode("C_Failure2");
        FailureMode c_failure3 = new FailureMode("C_Failure3");

        EndogenousFaultMode fm1 = new EndogenousFaultMode("A_Fault1");
        fm1.setArisingPDF("uniform(1,2)");

        faultModes.put("A_Fault1", new EndogenousFaultMode("A_Fault1"));
        faultModes.put("A_Fault2", new EndogenousFaultMode("A_Fault2"));
        faultModes.put("A_Fault3", new EndogenousFaultMode("A_Fault3"));
        faultModes.put("A_Fault4", new EndogenousFaultMode("A_Fault4"));
        faultModes.put("A_Fault5", new EndogenousFaultMode("A_Fault5"));

        faultModes.put("B_Fault1", new EndogenousFaultMode("B_Fault1"));
        faultModes.put("B_Fault2", new EndogenousFaultMode("B_Fault2"));
        faultModes.put("B_Fault3", new EndogenousFaultMode("B_Fault3"));

        faultModes.put("C_Fault1", new ExogenousFaultMode("C_Fault1"));
        faultModes.put("C_Fault2", new ExogenousFaultMode("C_Fault2"));
        faultModes.put("C_Fault3", new ExogenousFaultMode("C_Fault3"));
        faultModes.put("C_Fault4", new ExogenousFaultMode("C_Fault4"));
        faultModes.put("C_Fault5", new ExogenousFaultMode("C_Fault5"));
        faultModes.put("C_Fault6", new EndogenousFaultMode("C_Fault6"));

        BooleanExpression a_Failure1 = BooleanExpression.config("(A_Fault1)&&((A_Fault2) || (A_Fault3))", faultModes);
        BooleanExpression a_Failure2 = BooleanExpression.config("(A_Fault3)&&((A_Fault4) || (A_Fault5))", faultModes);
        BooleanExpression a_Failure3 = BooleanExpression.config("A_Fault2 || A_Fault5", faultModes);
        BooleanExpression b_Failure1 = BooleanExpression.config("B_Fault1 && B_Fault2", faultModes);
        BooleanExpression b_Failure2 = BooleanExpression.config("B_Fault1 && B_Fault3", faultModes);
        BooleanExpression c_Failure1 = BooleanExpression.config("C_Fault2 && C_Fault3", faultModes);
        BooleanExpression c_Failure2 = BooleanExpression.config("C_Fault6 && C_Fault4", faultModes);
        BooleanExpression c_Failure3 = BooleanExpression.config("C_Fault5 && C_Fault6", faultModes);

        a.addErrorMode(new ErrorMode("A_Propagation1", a_Failure1, a_failure1, "erlang(5,1)"));
        a.addErrorMode(new ErrorMode("A_Propagation2", a_Failure2, a_failure2, "erlang(7,1)"));
        a.addErrorMode(new ErrorMode("A_Propagation3", a_Failure3, a_failure3, "erlang(9,1)"));

        b.addErrorMode(new ErrorMode("B_Propagation1", b_Failure1, b_failure1, "erlang(6,1)"));
        b.addErrorMode(new ErrorMode("B_Propagation2", b_Failure2, b_failure2, "erlang(2,1)"));

        c.addErrorMode(new ErrorMode("C_Propagation1", c_Failure1, c_failure1,"erlang(2,1)"));
        c.addErrorMode(new ErrorMode("C_Propagation2", c_Failure2, c_failure2, "erlang(2,1)"));
        c.addErrorMode(new ErrorMode("C_Propagation3", c_Failure3, c_failure3, "erlang(2,1)"));

        /*
        failConnections.computeIfAbsent("A_Fault1", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault1"), faultModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault2", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault2"), faultModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault2", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault2"), faultModes.get("A_Failure3"), A_Propagation3, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault3", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault3"), faultModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault3", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault3"), faultModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault4", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault4"), faultModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault5", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault5"), faultModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault5", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("A_Fault5"), faultModes.get("A_Failure3"), A_Propagation3, metaComponents.get("Leaf_A")));

        failConnections.computeIfAbsent("B_Fault1", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("B_Fault1"), faultModes.get("B_Failure1"), B_Propagation1, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault1", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("B_Fault1"), faultModes.get("B_Failure2"), B_Propagation2, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault2", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("B_Fault2"), faultModes.get("B_Failure1"), B_Propagation1, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault3", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("B_Fault3"), faultModes.get("B_Failure2"), B_Propagation2, metaComponents.get("Leaf_B")));

        failConnections.computeIfAbsent("C_Fault6", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault6"), faultModes.get("C_Failure2"), C_Propagation2, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault6", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault6"), faultModes.get("C_Failure3"), C_Propagation3, metaComponents.get("Root_C")));

        failConnections.computeIfAbsent("C_Fault2", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault2"), faultModes.get("C_Failure1"), C_Propagation1, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault3", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault3"), faultModes.get("C_Failure1"), C_Propagation1, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault4", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault4"), faultModes.get("C_Failure2"), C_Propagation2, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault5", k -> new ArrayList<>()).add(new PropagationPort(faultModes.get("C_Fault5"), faultModes.get("C_Failure3"), C_Propagation3, metaComponents.get("Root_C")));
*/
        failConnections.computeIfAbsent("A_Failure1", k -> new ArrayList<>()).add(new PropagationPort(a_failure1, (ExogenousFaultMode) faultModes.get("C_Fault1"), metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("A_Failure2", k -> new ArrayList<>()).add(new PropagationPort(a_failure2, (ExogenousFaultMode) faultModes.get("C_Fault2"), metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("A_Failure3", k -> new ArrayList<>()).add(new PropagationPort(a_failure3, (ExogenousFaultMode) faultModes.get("C_Fault3"), metaComponents.get("Root_C")));
        a.addPropagationPort(failConnections.get("A_Failure1"));
        a.addPropagationPort(failConnections.get("A_Failure2"));
        a.addPropagationPort(failConnections.get("A_Failure3"));
        failConnections.computeIfAbsent("B_Failure1", k -> new ArrayList<>()).add(new PropagationPort(b_failure1, (ExogenousFaultMode) faultModes.get("C_Fault4"), metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("B_Failure2", k -> new ArrayList<>()).add(new PropagationPort(b_failure2, (ExogenousFaultMode) faultModes.get("C_Fault5"), metaComponents.get("Root_C")));
        b.addPropagationPort(failConnections.get("B_Failure1"));
        b.addPropagationPort(failConnections.get("B_Failure2"));

    }
}
