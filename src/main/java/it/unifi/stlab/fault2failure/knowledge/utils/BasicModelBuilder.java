package it.unifi.stlab.fault2failure.knowledge.utils;

import it.unifi.stlab.fault2failure.knowledge.composition.CompositionPort;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FailureMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
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
    private static HashMap<String, FailureMode> failModes;
    private static HashMap<String, List<PropagationPort>> failConnections;

    public static HashMap<String, MetaComponent> getMetaComponents() {
        return metaComponents;
    }

    public static HashMap<String, FailureMode> getFailModes() {
        return failModes;
    }

    public static HashMap<String, List<PropagationPort>> getFailConnections() {
        return failConnections;
    }
    public static void build() {
        metaComponents = new HashMap<>();
        failModes = new HashMap<>();
        failConnections = new HashMap<>();

        metaComponents.put("Root_C", new MetaComponent("Root_C"));
        CompositionPort abc = new CompositionPort(metaComponents.get("Root_C"));
        metaComponents.put("Leaf_A", new MetaComponent("Leaf_A"));
        metaComponents.put("Leaf_B", new MetaComponent("Leaf_B"));
        abc.addChild(metaComponents.get("Leaf_A"));
        abc.addChild(metaComponents.get("Leaf_B"));

        failModes.put("A_Failure1", new FailureMode("A_Failure1"));
        failModes.put("A_Failure2", new FailureMode("A_Failure2"));
        failModes.put("A_Failure3", new FailureMode("A_Failure3"));

        failModes.put("B_Failure1", new FailureMode("B_Failure1"));
        failModes.put("B_Failure2", new FailureMode("B_Failure2"));

        failModes.put("C_Failure1", new FailureMode("C_Failure1"));
        failModes.put("C_Failure2", new FailureMode("C_Failure2"));
        failModes.put("C_Failure3", new FailureMode("C_Failure3"));

        BooleanExpression a_Failure1 = BooleanExpression.config("(A_Fault1)&&((A_Fault2) || (A_Fault3))", failModes);
        BooleanExpression a_Failure2 = BooleanExpression.config("(A_Fault3)&&((A_Fault4) || (A_Fault5))", failModes);
        BooleanExpression a_Failure3 = BooleanExpression.config("A_Fault2 || A_Fault5", failModes);
        BooleanExpression b_Failure1 = BooleanExpression.config("B_Fault1 && B_Fault2", failModes);
        BooleanExpression b_Failure2 = BooleanExpression.config("B_Fault1 && B_Fault3", failModes);
        BooleanExpression c_Failure1 = BooleanExpression.config("C_Fault2 && C_Fault3", failModes);
        BooleanExpression c_Failure2 = BooleanExpression.config("C_Fault6 && C_Fault4", failModes);
        BooleanExpression c_Failure3 = BooleanExpression.config("C_Fault5 && C_Fault6", failModes);

        failModes.put("C_Fault1", new FailureMode("C_Fault1"));

        ErrorMode A_Propagation1 = new ErrorMode("A_Propagation1", a_Failure1, failModes.get("A_Failure1"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("5"), new BigDecimal("1")));
        ErrorMode A_Propagation2 = new ErrorMode("A_Propagation2", a_Failure2, failModes.get("A_Failure2"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("7"), new BigDecimal("1")));
        ErrorMode A_Propagation3 = new ErrorMode("A_Propagation3", a_Failure3, failModes.get("A_Failure3"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("9"), new BigDecimal("1")));

        ErrorMode B_Propagation1 = new ErrorMode("B_Propagation1", b_Failure1, failModes.get("B_Failure1"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("6"), new BigDecimal("1")));
        ErrorMode B_Propagation2 = new ErrorMode("B_Propagation2", b_Failure2, failModes.get("B_Failure2"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("2"), new BigDecimal("1")));

        ErrorMode C_Propagation1 = new ErrorMode("C_Propagation1", c_Failure1, failModes.get("C_Failure1"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("2"), new BigDecimal("1")));
        ErrorMode C_Propagation2 = new ErrorMode("C_Propagation2", c_Failure2, failModes.get("C_Failure2"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("2"), new BigDecimal("1")));
        ErrorMode C_Propagation3 = new ErrorMode("C_Propagation3", c_Failure3, failModes.get("C_Failure3"), StochasticTransitionFeature.newErlangInstance(Integer.parseInt("2"), new BigDecimal("1")));

        failConnections.computeIfAbsent("A_Fault1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault1"), failModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault2"), failModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault2"), failModes.get("A_Failure3"), A_Propagation3, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault3"), failModes.get("A_Failure1"), A_Propagation1, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault3"), failModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault4", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault4"), failModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault5", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault5"), failModes.get("A_Failure2"), A_Propagation2, metaComponents.get("Leaf_A")));
        failConnections.computeIfAbsent("A_Fault5", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Fault5"), failModes.get("A_Failure3"), A_Propagation3, metaComponents.get("Leaf_A")));

        failConnections.computeIfAbsent("B_Fault1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Fault1"), failModes.get("B_Failure1"), B_Propagation1, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Fault1"), failModes.get("B_Failure2"), B_Propagation2, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Fault2"), failModes.get("B_Failure1"), B_Propagation1, metaComponents.get("Leaf_B")));
        failConnections.computeIfAbsent("B_Fault3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Fault3"), failModes.get("B_Failure2"), B_Propagation2, metaComponents.get("Leaf_B")));

        failConnections.computeIfAbsent("C_Fault6", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault6"), failModes.get("C_Failure2"), C_Propagation2, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault6", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault6"), failModes.get("C_Failure3"), C_Propagation3, metaComponents.get("Root_C")));

        failConnections.computeIfAbsent("C_Fault2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault2"), failModes.get("C_Failure1"), C_Propagation1, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault3"), failModes.get("C_Failure1"), C_Propagation1, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault4", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault4"), failModes.get("C_Failure2"), C_Propagation2, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("C_Fault5", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("C_Fault5"), failModes.get("C_Failure3"), C_Propagation3, metaComponents.get("Root_C")));

        failConnections.computeIfAbsent("A_Failure1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Failure1"), failModes.get("C_Fault1"), null, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("A_Failure2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Failure2"), failModes.get("C_Fault2"), null, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("A_Failure3", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("A_Failure3"), failModes.get("C_Fault3"), null, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("B_Failure1", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Failure1"), failModes.get("C_Fault4"), null, metaComponents.get("Root_C")));
        failConnections.computeIfAbsent("B_Failure2", k -> new ArrayList<>()).add(new PropagationPort(failModes.get("B_Failure2"), failModes.get("C_Fault5"), null, metaComponents.get("Root_C")));

    }
}
