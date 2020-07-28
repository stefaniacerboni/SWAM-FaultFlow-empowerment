package it.unifi.stlab.fault2failure.knowledge.translator;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import it.unifi.stlab.fault2failure.operational.Component;
import it.unifi.stlab.fault2failure.operational.Failure;
import org.oristool.models.pn.Priority;
import org.oristool.models.stpn.MarkingExpr;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;

/**
 * Class that implements the method translate that transforms the system OO into a petriNet model.
 * This means translating all the errorModes and FailureMode into places and transitions and connecting them following the
 * propagation ports.
 */
public class PetriNetTranslator implements Translator {
    private final PetriNet net;
    private Marking marking;
    
    public PetriNetTranslator() {
    	net = new PetriNet();
    	marking = new Marking();
    }

    public void translate(List<PropagationPort> failConnections) {
        //First add ErrorModes to the net, thus the ErrorMode and its outgoing failure become places and
        //between them there's a transition with the ErrorMode's enabling function
        for (PropagationPort pp : failConnections) {
            if (pp.getErrorMode() != null && net.getPlace(pp.getErrorMode().getName()) == null) {
                Place a = net.addPlace(pp.getErrorMode().getName());
                Place b = net.addPlace(pp.getErrorMode().getOutgoingFailure().getDescription());
                Transition t = net.addTransition(getTransitionName(b.getName()));
                t.addFeature(new EnablingFunction(pp.getErrorMode().getActivationFunction()));
                t.addFeature(pp.getErrorMode().getTimetofailurePDF());
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);
                marking.setTokens(a, 1); //Every ErrorMode(Propagation) already has a token in its place.
                marking.setTokens(b, 0); //Everything else doesn't have a token yet.
                //^avoidable since everything's at 0 if it's not stated different
            }
        }
        //Then add transitions between Failure at low level to fault at higher level.
        for (PropagationPort pp : failConnections) {
            //Nelle failConnections questi collegamenti sono evidenziati non specificando il propagationMechanism che li gestisce
            //Perché sono, effettivamente, esenti da errorMode, la propagazione è immediata
            if (pp.getErrorMode() == null) {
                Place a = net.getPlace(pp.getFailIn().getDescription());
                Place b = net.addPlace(pp.getFailOut().getDescription());
                Transition t = net.addTransition(getTransitionName(b.getName()));
                TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
                if(tf == null) {
                    t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", net)));
                    t.addFeature(new Priority(0));
                }
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);
            }
        }
        //Finally add the endogenous fault as places: if a fault it's not already into the net
        // then it's not a result of a propagation, this means that it's an endogenous fault
        for (PropagationPort pp : failConnections) {
            if (net.getPlace(pp.getFailIn().getDescription()) == null) {
                Place a = net.addPlace(pp.getFailIn().getDescription() + "Occurrence");
                Place b = net.addPlace(pp.getFailIn().getDescription());
                Transition t = net.addTransition(getTransitionName(a.getName()));
                t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", net)));
                t.addFeature(new Priority(0));
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);

            }
        }
    }

    /**
     * Decorate the already instanced petri net with information coming at the Operational Level: this includes
     * -adding timestamp to failure occurrences' transitions
     * -decorate failure occurrence's places with tokens if they're active
     * @param failure the instance of Failure created at Operational Level in a Scenario, that has to be translated
     *                into a Place in the PetriNet.
     */
    public void decorateOccurrence(Failure failure, BigDecimal timestamp) {
        Place a = net.getPlace((failure.getFailureMode().getDescription()+"Occurrence"));
        marking.setTokens(a,1);
        Transition t = net.getTransition(getTransitionName(a.getName()));
        TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
        if(tf != null)
            t.removeFeature(StochasticTransitionFeature.class);
        t.addFeature(StochasticTransitionFeature.newDeterministicInstance(timestamp, MarkingExpr.from("1", net)));
    }

    public void translateFromSystem(HashMap<String, BigDecimal> inFailures, List<Component> system){

    }

    /**
     * Simple method that returns the name of a Transition accordingly to the place connected to it. Place->Transition
     * Places' names will begin with a capital letter, transitions' names will start with the first letter in lower case.
     * @param placeName name of the place before the transition. PlaceName->TransitionName
     * @return the name of a Transition accordingly to the place connected to it
     */
    public static String getTransitionName(String placeName){
        return Character.toString(placeName.charAt(0)).toLowerCase() + placeName.substring(1);
    }


    public PetriNet getPetriNet() {
    	return net;
    }

    public Marking getMarking() {
    	return marking;
    }
}
