package it.unifi.stlab.fault2failure.knowledge.translator;

import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import it.unifi.stlab.fault2failure.operational.ConcreteComponent;
import it.unifi.stlab.fault2failure.operational.Fault;
import it.unifi.stlab.fault2failure.operational.Scenario;
import org.junit.Before;
import org.junit.Test;
import org.oristool.models.pn.Priority;
import org.oristool.models.stpn.MarkingExpr;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class PetriNetTranslatorTest {
    private PetriNetTranslator pnt;

    private PetriNet expectedPN;
    private PetriNet actualPN;

    private Marking expectedMarking;
    private Marking actualMarking;

    @Before
    public void setup() {
        buildExpectedPN();
        buildActualPN();
    }

    private void buildActualPN() {
        pnt = new PetriNetTranslator();
        System basic = BasicModelBuilder.getInstance().getSystem();
        pnt.translate(basic);
        actualPN = pnt.getPetriNet();
        actualMarking = pnt.getMarking();
    }

    private void buildExpectedPN() {
        expectedPN = new PetriNet();
        expectedMarking = new Marking();

        //Generating Nodes
        Place A_Failure1 = expectedPN.addPlace("A_Failure1");
        Place A_Failure2 = expectedPN.addPlace("A_Failure2");
        Place A_Failure3 = expectedPN.addPlace("A_Failure3");
        Place A_Fault1 = expectedPN.addPlace("A_Fault1");
        Place A_Fault1Occurrence = expectedPN.addPlace("A_Fault1Occurrence");
        Place A_Fault2 = expectedPN.addPlace("A_Fault2");
        Place A_Fault2Occurrence = expectedPN.addPlace("A_Fault2Occurrence");
        Place A_Fault3 = expectedPN.addPlace("A_Fault3");
        Place A_Fault3Occurrence = expectedPN.addPlace("A_Fault3Occurrence");
        Place A_Fault4 = expectedPN.addPlace("A_Fault4");
        Place A_Fault4Occurrence = expectedPN.addPlace("A_Fault4Occurrence");
        Place A_Fault5 = expectedPN.addPlace("A_Fault5");
        Place A_Fault5Occurrence = expectedPN.addPlace("A_Fault5Occurrence");
        Place A_Propagation1 = expectedPN.addPlace("A_Propagation1");
        Place A_Propagation2 = expectedPN.addPlace("A_Propagation2");
        Place A_Propagation3 = expectedPN.addPlace("A_Propagation3");
        Place B_Failure1 = expectedPN.addPlace("B_Failure1");
        Place B_Failure2 = expectedPN.addPlace("B_Failure2");
        Place B_Fault1 = expectedPN.addPlace("B_Fault1");
        Place B_Fault1Occurrence = expectedPN.addPlace("B_Fault1Occurrence");
        Place B_Fault2 = expectedPN.addPlace("B_Fault2");
        Place B_Fault2Occurrence = expectedPN.addPlace("B_Fault2Occurrence");
        Place B_Fault3 = expectedPN.addPlace("B_Fault3");
        Place B_Fault3Occurrence = expectedPN.addPlace("B_Fault3Occurrence");
        Place B_Propagation1 = expectedPN.addPlace("B_Propagation1");
        Place B_Propagation2 = expectedPN.addPlace("B_Propagation2");
        Place C_Failure1 = expectedPN.addPlace("C_Failure1");
        Place C_Failure2 = expectedPN.addPlace("C_Failure2");
        Place C_Failure3 = expectedPN.addPlace("C_Failure3");
        Place C_Fault1 = expectedPN.addPlace("C_Fault1");
        Place C_Fault2 = expectedPN.addPlace("C_Fault2");
        Place C_Fault3 = expectedPN.addPlace("C_Fault3");
        Place C_Fault4 = expectedPN.addPlace("C_Fault4");
        Place C_Fault5 = expectedPN.addPlace("C_Fault5");
        Place C_Fault6 = expectedPN.addPlace("C_Fault6");
        Place C_Fault6Occurrence = expectedPN.addPlace("C_Fault6Occurrence");
        Place C_Propagation1 = expectedPN.addPlace("C_Propagation1");
        Place C_Propagation2 = expectedPN.addPlace("C_Propagation2");
        Place C_Propagation3 = expectedPN.addPlace("C_Propagation3");
        Transition A_Failure1toFaults = expectedPN.addTransition("A_Failure1toFaults");
        Transition A_Failure2toFaults = expectedPN.addTransition("A_Failure2toFaults");
        Transition A_Failure3toFaults = expectedPN.addTransition("A_Failure3toFaults");
        Transition B_Failure1toFaults = expectedPN.addTransition("B_Failure1toFaults");
        Transition B_Failure2toFaults = expectedPN.addTransition("B_Failure2toFaults");
        Transition a_Failure1 = expectedPN.addTransition("a_Failure1");
        Transition a_Failure2 = expectedPN.addTransition("a_Failure2");
        Transition a_Failure3 = expectedPN.addTransition("a_Failure3");
        Transition a_Fault1Occurrence = expectedPN.addTransition("a_Fault1Occurrence");
        Transition a_Fault2Occurrence = expectedPN.addTransition("a_Fault2Occurrence");
        Transition a_Fault3Occurrence = expectedPN.addTransition("a_Fault3Occurrence");
        Transition a_Fault4Occurrence = expectedPN.addTransition("a_Fault4Occurrence");
        Transition a_Fault5Occurrence = expectedPN.addTransition("a_Fault5Occurrence");
        Transition b_Failure1 = expectedPN.addTransition("b_Failure1");
        Transition b_Failure2 = expectedPN.addTransition("b_Failure2");
        Transition b_Fault1Occurrence = expectedPN.addTransition("b_Fault1Occurrence");
        Transition b_Fault2Occurrence = expectedPN.addTransition("b_Fault2Occurrence");
        Transition b_Fault3Occurrence = expectedPN.addTransition("b_Fault3Occurrence");
        Transition c_Failure1 = expectedPN.addTransition("c_Failure1");
        Transition c_Failure2 = expectedPN.addTransition("c_Failure2");
        Transition c_Failure3 = expectedPN.addTransition("c_Failure3");
        Transition c_Fault6Occurrence = expectedPN.addTransition("c_Fault6Occurrence");

        //Generating Connectors
        expectedPN.addPrecondition(B_Fault1Occurrence, b_Fault1Occurrence);
        expectedPN.addPostcondition(b_Fault1Occurrence, B_Fault1);
        expectedPN.addPrecondition(B_Propagation1, b_Failure1);
        expectedPN.addPostcondition(b_Failure1, B_Failure1);
        expectedPN.addPrecondition(B_Failure1, B_Failure1toFaults);
        expectedPN.addPostcondition(B_Failure1toFaults, C_Fault4);
        expectedPN.addPrecondition(C_Propagation2, c_Failure2);
        expectedPN.addPostcondition(c_Failure2, C_Failure2);
        expectedPN.addPrecondition(B_Propagation2, b_Failure2);
        expectedPN.addPostcondition(b_Failure2, B_Failure2);
        expectedPN.addPrecondition(B_Failure2, B_Failure2toFaults);
        expectedPN.addPostcondition(B_Failure2toFaults, C_Fault5);
        expectedPN.addPrecondition(C_Propagation3, c_Failure3);
        expectedPN.addPostcondition(c_Failure3, C_Failure3);
        expectedPN.addPrecondition(B_Fault2Occurrence, b_Fault2Occurrence);
        expectedPN.addPostcondition(b_Fault2Occurrence, B_Fault2);
        expectedPN.addPrecondition(B_Fault3Occurrence, b_Fault3Occurrence);
        expectedPN.addPostcondition(b_Fault3Occurrence, B_Fault3);
        expectedPN.addPrecondition(C_Fault6Occurrence, c_Fault6Occurrence);
        expectedPN.addPostcondition(c_Fault6Occurrence, C_Fault6);
        expectedPN.addPrecondition(A_Fault1Occurrence, a_Fault1Occurrence);
        expectedPN.addPostcondition(a_Fault1Occurrence, A_Fault1);
        expectedPN.addPrecondition(A_Propagation1, a_Failure1);
        expectedPN.addPostcondition(a_Failure1, A_Failure1);
        expectedPN.addPrecondition(A_Failure1, A_Failure1toFaults);
        expectedPN.addPostcondition(A_Failure1toFaults, C_Fault1);
        expectedPN.addPrecondition(A_Fault2Occurrence, a_Fault2Occurrence);
        expectedPN.addPostcondition(a_Fault2Occurrence, A_Fault2);
        expectedPN.addPrecondition(A_Propagation3, a_Failure3);
        expectedPN.addPostcondition(a_Failure3, A_Failure3);
        expectedPN.addPrecondition(A_Failure3, A_Failure3toFaults);
        expectedPN.addPostcondition(A_Failure3toFaults, C_Fault3);
        expectedPN.addPrecondition(C_Propagation1, c_Failure1);
        expectedPN.addPostcondition(c_Failure1, C_Failure1);
        expectedPN.addPrecondition(A_Fault3Occurrence, a_Fault3Occurrence);
        expectedPN.addPostcondition(a_Fault3Occurrence, A_Fault3);
        expectedPN.addPrecondition(A_Propagation2, a_Failure2);
        expectedPN.addPostcondition(a_Failure2, A_Failure2);
        expectedPN.addPrecondition(A_Failure2, A_Failure2toFaults);
        expectedPN.addPostcondition(A_Failure2toFaults, C_Fault2);
        expectedPN.addPrecondition(A_Fault4Occurrence, a_Fault4Occurrence);
        expectedPN.addPostcondition(a_Fault4Occurrence, A_Fault4);
        expectedPN.addPrecondition(A_Fault5Occurrence, a_Fault5Occurrence);
        expectedPN.addPostcondition(a_Fault5Occurrence, A_Fault5);

        //Generating Properties
        expectedMarking.setTokens(A_Failure1, 0);
        expectedMarking.setTokens(A_Failure2, 0);
        expectedMarking.setTokens(A_Failure3, 0);
        expectedMarking.setTokens(A_Fault1, 0);
        expectedMarking.setTokens(A_Fault1Occurrence, 0);
        expectedMarking.setTokens(A_Fault2, 0);
        expectedMarking.setTokens(A_Fault2Occurrence, 0);
        expectedMarking.setTokens(A_Fault3, 0);
        expectedMarking.setTokens(A_Fault3Occurrence, 0);
        expectedMarking.setTokens(A_Fault4, 0);
        expectedMarking.setTokens(A_Fault4Occurrence, 0);
        expectedMarking.setTokens(A_Fault5, 0);
        expectedMarking.setTokens(A_Fault5Occurrence, 0);
        expectedMarking.setTokens(A_Propagation1, 1);
        expectedMarking.setTokens(A_Propagation2, 1);
        expectedMarking.setTokens(A_Propagation3, 1);
        expectedMarking.setTokens(B_Failure1, 0);
        expectedMarking.setTokens(B_Failure2, 0);
        expectedMarking.setTokens(B_Fault1, 0);
        expectedMarking.setTokens(B_Fault1Occurrence, 0);
        expectedMarking.setTokens(B_Fault2, 0);
        expectedMarking.setTokens(B_Fault2Occurrence, 0);
        expectedMarking.setTokens(B_Fault3, 0);
        expectedMarking.setTokens(B_Fault3Occurrence, 0);
        expectedMarking.setTokens(B_Propagation1, 1);
        expectedMarking.setTokens(B_Propagation2, 1);
        expectedMarking.setTokens(C_Failure1, 0);
        expectedMarking.setTokens(C_Failure2, 0);
        expectedMarking.setTokens(C_Failure3, 0);
        expectedMarking.setTokens(C_Fault1, 0);
        expectedMarking.setTokens(C_Fault2, 0);
        expectedMarking.setTokens(C_Fault3, 0);
        expectedMarking.setTokens(C_Fault4, 0);
        expectedMarking.setTokens(C_Fault5, 0);
        expectedMarking.setTokens(C_Fault6, 0);
        expectedMarking.setTokens(C_Fault6Occurrence, 0);
        expectedMarking.setTokens(C_Propagation1, 1);
        expectedMarking.setTokens(C_Propagation2, 1);
        expectedMarking.setTokens(C_Propagation3, 1);
        A_Failure1toFaults.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", expectedPN)));
        A_Failure1toFaults.addFeature(new Priority(0));
        A_Failure2toFaults.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", expectedPN)));
        A_Failure2toFaults.addFeature(new Priority(0));
        A_Failure3toFaults.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", expectedPN)));
        A_Failure3toFaults.addFeature(new Priority(0));
        B_Failure1toFaults.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", expectedPN)));
        B_Failure1toFaults.addFeature(new Priority(0));
        B_Failure2toFaults.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", expectedPN)));
        B_Failure2toFaults.addFeature(new Priority(0));
        a_Failure1.addFeature(new EnablingFunction("(A_Fault1>0)&&((A_Fault2>0)||(A_Fault3>0))"));
        a_Failure1.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("5"), new BigDecimal("1")));
        a_Failure2.addFeature(new EnablingFunction("(A_Fault3>0)&&((A_Fault4>0)||(A_Fault5>0))"));
        a_Failure2.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("7"), new BigDecimal("1")));
        a_Failure3.addFeature(new EnablingFunction("(A_Fault2>0)||(A_Fault5>0)"));
        a_Failure3.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("9"), new BigDecimal("1")));
        a_Fault1Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("10"), MarkingExpr.from("1", expectedPN)));
        a_Fault1Occurrence.addFeature(new Priority(0));
        a_Fault2Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("13"), MarkingExpr.from("1", expectedPN)));
        a_Fault2Occurrence.addFeature(new Priority(0));
        a_Fault3Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("16"), MarkingExpr.from("1", expectedPN)));
        a_Fault3Occurrence.addFeature(new Priority(0));
        a_Fault4Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", expectedPN)));
        a_Fault4Occurrence.addFeature(new Priority(0));
        a_Fault5Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", expectedPN)));
        a_Fault5Occurrence.addFeature(new Priority(0));
        b_Failure1.addFeature(new EnablingFunction("(B_Fault1>0)&&(B_Fault2>0)"));
        b_Failure1.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("6"), new BigDecimal("1")));
        b_Failure2.addFeature(new EnablingFunction("(B_Fault1>0)&&(B_Fault3>0)"));
        b_Failure2.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("2"), new BigDecimal("1")));
        b_Fault1Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("12"), MarkingExpr.from("1", expectedPN)));
        b_Fault1Occurrence.addFeature(new Priority(0));
        b_Fault2Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("18"), MarkingExpr.from("1", expectedPN)));
        b_Fault2Occurrence.addFeature(new Priority(0));
        b_Fault3Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", expectedPN)));
        b_Fault3Occurrence.addFeature(new Priority(0));
        c_Failure1.addFeature(new EnablingFunction("(C_Fault2>0)&&(C_Fault3>0)"));
        c_Failure1.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("2"), new BigDecimal("1")));
        c_Failure2.addFeature(new EnablingFunction("(C_Fault6>0)&&(C_Fault4>0)"));
        c_Failure2.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("2"), new BigDecimal("1")));
        c_Failure3.addFeature(new EnablingFunction("(C_Fault5>0)&&(C_Fault6>0)"));
        c_Failure3.addFeature(StochasticTransitionFeature.newErlangInstance(new Integer("2"), new BigDecimal("1")));
        c_Fault6Occurrence.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("17"), MarkingExpr.from("1", expectedPN)));
        c_Fault6Occurrence.addFeature(new Priority(0));
    }

    @Test
    public void testNumberOfPlaces() {
        assertEquals(expectedPN.getPlaces().size(), actualPN.getPlaces().size());
    }

    @Test
    public void testNumberOfTransitions() {
        assertEquals(expectedPN.getTransitions().size(), actualPN.getTransitions().size());
    }

    @Test
    public void testMarkingsDefaultModel() {
        assertEquals(expectedMarking, actualMarking);
    }

    @Test
    public void testPlaces() {
        for (String place : expectedPN.getPlaceNames()) {
            if (!actualPN.getPlaceNames().contains(place)) {
                fail();
            }
        }
    }

    @Test
    public void testTransitions() {
        //TODO verificare i nomi e poi rimuovere il blocco una volta risolto il problema
        // BEGIN : solo per trovare il problema


        List<String> exptrnas = new ArrayList<String>(expectedPN.getTransitionNames());
        List<String> acttrnas = new ArrayList<String>(actualPN.getTransitionNames());
        Collections.sort(exptrnas);
        Collections.sort(acttrnas);
        java.lang.System.out.println(acttrnas);
        java.lang.System.out.println(exptrnas);
        // END : solo per trovare il problema

        for (String transition : expectedPN.getTransitionNames()) {
            if (!actualPN.getTransitionNames().contains(transition)) {
                fail();
            }
        }
    }

    @Test
    public void testEnablingFunctions() {
        for (Transition t1 : expectedPN.getTransitions()) {
            Transition t2 = actualPN.getTransition(t1.getName());
            EnablingFunction expectedEF = t1.getFeature(EnablingFunction.class);
            if (expectedEF != null) {
                //if there's an enabling function in the transition, check if it's semantically equal to the other one
                if (!expectedEF.toString().equals(t2.getFeature(EnablingFunction.class).toString()))
                    fail();
            } else {
                //if there's no enabling function in the transition, check that also t2 has no enabling function
                assertNull(t2.getFeature(EnablingFunction.class));
            }
        }
    }

    @Test
    public void testPDF() {
        for (Transition t1 : expectedPN.getTransitions()) {
            Transition t2 = actualPN.getTransition(t1.getName());
            StochasticTransitionFeature expectedSTF = t1.getFeature(StochasticTransitionFeature.class);
            StochasticTransitionFeature actualSTF = t2.getFeature(StochasticTransitionFeature.class);
            if (expectedSTF != null) {
                //if there's a StochasticTransitionFeature in the transition, check if they're the same distribution
                if (!expectedSTF.density().getClass().equals(actualSTF.density().getClass()))
                    fail();
                else {
                    //if they're the same distribution, check if they have the same parameters
                    if (!expectedSTF.density().toMathematicaString().equals(actualSTF.density().toMathematicaString()))
                        fail();
                }
            } else {
                //if there's no StochasticTransitionFeature in the transition, check that also t2 has no StochasticTransitionFeature
                assertNull(actualSTF);
            }
        }
    }

    private void setupScenario() {
        HashMap<String, FaultMode> failModes = BasicModelBuilder.getInstance().getFaultModes();

        Fault A_fault1Occurred = new Fault("A_fault1Occurred", failModes.get("A_Fault1"));
        Fault A_fault2Occurred = new Fault("A_fault2Occurred", failModes.get("A_Fault2"));
        Fault A_fault3Occurred = new Fault("A_fault3Occurred", failModes.get("A_Fault3"));

        Fault B_fault1Occurred = new Fault("B_fault1Occurred", failModes.get("B_Fault1"));
        Fault B_fault2Occurred = new Fault("B_fault2Occurred", failModes.get("B_Fault2"));
        Fault C_fault6Occurred = new Fault("C_fault6Occurred", failModes.get("C_Fault6"));

        System system = BasicModelBuilder.getInstance().getSystem();

        Scenario scenario = new Scenario(system);
        Map<String, ConcreteComponent> current_system = scenario.getCurrentSystemMap();

        scenario.addFault(A_fault1Occurred, BigDecimal.valueOf(10), current_system.get("Leaf_A_Base"));
        scenario.addFault(A_fault2Occurred, BigDecimal.valueOf(13), current_system.get("Leaf_A_Base"));
        scenario.addFault(A_fault3Occurred, BigDecimal.valueOf(16), current_system.get("Leaf_A_Base"));
        scenario.addFault(B_fault1Occurred, BigDecimal.valueOf(12), current_system.get("Leaf_B_Base"));
        scenario.addFault(B_fault2Occurred, BigDecimal.valueOf(18), current_system.get("Leaf_B_Base"));
        scenario.addFault(C_fault6Occurred, BigDecimal.valueOf(17), current_system.get("Root_C_Base"));
        scenario.propagate();
        scenario.accept(pnt);

        this.actualMarking = pnt.getMarking();
        this.actualPN = pnt.getPetriNet();
    }

    @Test
    public void testScenarioMarkings() {

        setupScenario();
        //all the occurrences will be propagated, the markings that we'll have should be: all the occurrences in the macroscopic event+
        //the propagations that were already there + the failures propagated (which, from the word document, we get to know that are 4:
        // A_Failure1, A_Failure3, B_Failure1, C_Failure2)

        //So, I add them to the expected marking and check if the results are the same
        expectedMarking.setTokens(expectedPN.getPlace("A_Fault1Occurrence"), 1);
        expectedMarking.setTokens(expectedPN.getPlace("A_Fault2Occurrence"), 1);
        expectedMarking.setTokens(expectedPN.getPlace("A_Fault3Occurrence"), 1);
        expectedMarking.setTokens(expectedPN.getPlace("B_Fault1Occurrence"), 1);
        expectedMarking.setTokens(expectedPN.getPlace("B_Fault2Occurrence"), 1);
        expectedMarking.setTokens(expectedPN.getPlace("C_Fault6Occurrence"), 1);
        assertEquals(expectedMarking, actualMarking);
    }
}
