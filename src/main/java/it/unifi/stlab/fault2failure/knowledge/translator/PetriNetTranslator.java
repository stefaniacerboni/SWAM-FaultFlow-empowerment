package it.unifi.stlab.fault2failure.knowledge.translator;

import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Fault;
import org.oristool.models.pn.Priority;
import org.oristool.models.stpn.MarkingExpr;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Class that implements the method translate that transforms the system OO into a petriNet model.
 * This means translating all the errorModes and FailureMode into places and transitions and connecting them following the
 * propagation ports.
 */
public class PetriNetTranslator implements Translator {
    private final PetriNet net;
    private final Marking marking;

    public PetriNetTranslator() {
        net = new PetriNet();
        marking = new Marking();
    }

    /**
     * Simple method that returns the name of a Transition accordingly to the place connected to it. Place->Transition
     * Places' names will begin with a capital letter, transitions' names will start with the first letter in lower case.
     *
     * @param placeName name of the place before the transition. PlaceName->TransitionName
     * @return the name of a Transition accordingly to the place connected to it
     */
    public static String getTransitionName(String placeName) {
        return Character.toString(placeName.charAt(0)).toLowerCase() + placeName.substring(1);
    }

    public void translate(System system) {
        //First add ErrorModes to the net, thus the ErrorMode and its outgoing failure become places and
        //between them there's a transition with the ErrorMode's enabling function
        Place a, b;
        Transition t;
        for (MetaComponent metaComponent : system.getComponents()) {
            for (ErrorMode e : metaComponent.getErrorModes()) {
                //add ErrorMode and its failureMode
                a = net.addPlace(e.getName());
                b = net.addPlace(e.getOutgoingFailure().getDescription());
                t = net.addTransition(getTransitionName(b.getName()));
                t.addFeature(new EnablingFunction(e.getActivationFunction()));
                t.addFeature(e.getTimetofailurePDF());
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);
                marking.setTokens(a, 1);

                //add its faultModes

                List<FaultMode> inFaults = e.getInputFaultModes();
                for (FaultMode fault : inFaults) {
                    if (net.getPlace(fault.getName()) == null) {
                        //if fault is not already in the net
                        b = net.addPlace(fault.getName());
                        if (fault instanceof EndogenousFaultMode) {
                            a = net.addPlace(fault.getName() + "Occurrence");
                            t = net.addTransition(getTransitionName(a.getName()));
                            if (((EndogenousFaultMode) fault).getArisingPDF() != null)
                                t.addFeature(((EndogenousFaultMode) fault).getArisingPDF());
                            else
                                t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", net)));
                            t.addFeature(new Priority(0));
                            net.addPrecondition(a, t);
                            net.addPostcondition(t, b);
                        }
                    }
                }
            }
            //cycle through propPorts to connect propagatedFailureMode to its exogenousFaultMode
            if (!metaComponent.getPropagationPort().isEmpty()) {
                for (PropagationPort pp : metaComponent.getPropagationPort()) {
                    a = net.getPlace(pp.getPropagatedFailureMode().getDescription());
                    b = net.addPlace(pp.getExogenousFaultMode().getName());
                    t = net.addTransition(getTransitionName(b.getName()));
                    TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
                    if (tf == null) {
                        t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", net)));
                        t.addFeature(new Priority(0));
                    }
                    net.addPrecondition(a, t);
                    net.addPostcondition(t, b);
                }
            }
        }
    }

    /**
     * Decorate the already instanced petri net with information coming at the Operational Level: this includes
     * -adding timestamp to failure occurrences' transitions
     * -decorate failure occurrence's places with tokens if they're active
     *
     * @param fault the instance of Failure created at Operational Level in a Scenario, that has to be translated
     *              into a Place in the PetriNet.
     */
    public void decorateOccurrence(Fault fault, BigDecimal timestamp) {
        Place a = net.getPlace((fault.getFaultMode().getName() + "Occurrence"));
        marking.setTokens(a, 1);
        Transition t = net.getTransition(getTransitionName(a.getName()));
        TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
        if (tf != null)
            t.removeFeature(StochasticTransitionFeature.class);
        t.addFeature(StochasticTransitionFeature.newDeterministicInstance(timestamp, MarkingExpr.from("1", net)));
    }

    public PetriNet getPetriNet() {
        return net;
    }

    public Marking getMarking() {
        return marking;
    }
}
