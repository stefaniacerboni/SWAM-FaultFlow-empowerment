package it.unifi.stlab.fault2failure.knowledge.translator;

import it.unifi.stlab.exporter.PetriNetExportMethod;
import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.*;
import it.unifi.stlab.fault2failure.knowledge.utils.PDFParser;
import it.unifi.stlab.fault2failure.knowledge.utils.SampleGenerator;
import it.unifi.stlab.fault2failure.operational.Error;
import it.unifi.stlab.fault2failure.operational.Event;
import it.unifi.stlab.fault2failure.operational.Failure;
import it.unifi.stlab.fault2failure.operational.Fault;
import org.oristool.models.pn.Priority;
import org.oristool.models.stpn.MarkingExpr;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public void translate(System system, PetriNetExportMethod method){

        //First add ErrorModes to the net, thus the ErrorMode and its outgoing failure become places and
        //between them there's a transition with the ErrorMode's enabling function
        Place a, b;
        Transition t;
        for (Component component : system.getComponents()) {
            for (ErrorMode e : component.getErrorModes()) {
                //add ErrorMode and its failureMode
                a = net.addPlace(e.getName());
                b = net.addPlace(e.getOutgoingFailure().getDescription());
                t = net.addTransition(getTransitionName(b.getName()));
                t.addFeature(new EnablingFunction(e.getActivationFunction()));
                t.addFeature(PDFParser.parseRealDistributionToStochasticTransitionFeature(e.getTimetofailurePDF()));
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
                            if (((EndogenousFaultMode) fault).getArisingPDF() != null) {
                                if(method == PetriNetExportMethod.FAULT_INJECTION) {
                                    Double sample = SampleGenerator.generate(((EndogenousFaultMode) fault).getArisingPDFToString());
                                    t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal(""+sample), MarkingExpr.from("1", net)));
                                    marking.setTokens(a, 1);
                                }
                                else
                                    t.addFeature(PDFParser.parseRealDistributionToStochasticTransitionFeature(((EndogenousFaultMode) fault).getArisingPDF()));
                            }
                            else
                                t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("1"), MarkingExpr.from("1", net)));
                            t.addFeature(new Priority(0));
                            net.addPrecondition(a, t);
                            net.addPostcondition(t, b);
                        }
                    }
                }
            }
            //cycle through propPorts to connect propagatedFailureMode to its exogenousFaultModeS
            if (!component.getPropagationPorts().isEmpty()) {
                for (PropagationPort pp : component.getPropagationPorts()) {
                    a = net.getPlace(pp.getPropagatedFailureMode().getDescription());
                    b = net.addPlace(pp.getExogenousFaultMode().getName());
                    t = net.getTransition(a.getName()+"toFaults");
                    if(t==null) {
                        t = net.addTransition(a.getName() + "toFaults");
                        TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
                        if (tf == null) {
                            t.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", net)));
                            t.addFeature(new Priority(0));
                        }
                        net.addPrecondition(a, t);
                    }
                    net.addPostcondition(t, b);
                }
            }
        }
    }

    public void translate(System system) {
        translate(system, PetriNetExportMethod.FAULT_ANALYSIS);
    }

    /**
     * Decorate the already instanced petri net with information coming at the Operational Level: this includes
     * -the event (which can be an Error, Fault or Failure) and its timestamp
     * This method adds the timestamp to Fault/Failure occurrences' transitions and
     * decorates Fault/Failure occurrence's places with tokens if they're active.
     * Changes the ErroMode's transition with a deterministic transition if specified.
     *
     * @param event the instance of an event created at Operational Level in a Scenario, that has to be translated
     *              into a Place in the PetriNet. This can be a Fault, Error or Failure
     * @param timestamp the moment in which the event is expected to occur. For an error, this parameter specifies
     *                  a different delay (deterministic) in the propagation of the failure.
     */
    public void decorate(Event event, BigDecimal timestamp, PetriNetTranslatorMethod pntMethod){
        switch(event.getClass().getSimpleName()){
            case "Error":
                decorateError((Error) event, timestamp);
                break;
            case "Failure":
                decorateFailure((Failure) event, timestamp, pntMethod);
                break;
            default: decorateOccurrence((Fault) event, timestamp, pntMethod);
        }
    }
    private void decorateOccurrence(Fault fault, BigDecimal timestamp, PetriNetTranslatorMethod pntMethod) {
        if(fault.getFaultMode() instanceof ExogenousFaultMode) {
            decorateExogenousFaultOccurrence(fault, pntMethod);
        }
        Place a = net.getPlace((fault.getFaultMode().getName() + "Occurrence"));
        marking.setTokens(a, 1);
        Transition t = net.getTransition(getTransitionName(a.getName()));
        TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
        if (tf != null)
            t.removeFeature(StochasticTransitionFeature.class);
        t.addFeature(PDFParser.parseStringToStochasticTransitionFeature("dirac("+timestamp.toString()+")"));
    }

    private void decorateExogenousFaultOccurrence(Fault fault, PetriNetTranslatorMethod pntMethod){
        Place a = net.addPlace(fault.getFaultMode().getName() + "Occurrence");
        Transition t = net.addTransition(getTransitionName(a.getName()));
        Place b = net.getPlace(fault.getFaultMode().getName());
        List<Transition> transitionsToEdit = new ArrayList<>();
        for (Transition transition : net.getTransitions()) {
            Postcondition pc = net.getPostcondition(transition, b);
            if (pc != null) {
                transitionsToEdit.add(pc.getTransition());
            }
        }
        switch(pntMethod) {
            case CONCURRENT:
                for (Transition transition : transitionsToEdit) {
                    if (net.getPostconditions(transition).size() > 1) {
                        net.removePostcondition(net.getPostcondition(transition, b));
                        Place a1 = net.addPlace("To" + fault.getFaultMode().getName());
                        net.addPostcondition(transition, a1);
                        Transition t1 = net.addTransition(getTransitionName(fault.getFaultMode().getName()));
                        net.addPrecondition(a1, t1);
                        net.addPostcondition(t1, b);
                        t1.addFeature(new EnablingFunction("(" + b.getName() + "==0)"));
                        t1.addFeature(StochasticTransitionFeature.newDeterministicInstance(new BigDecimal("0"), MarkingExpr.from("1", net)));
                    } else {
                        transition.addFeature(new EnablingFunction("(" + b.getName() + "==0)"));
                    }
                }
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);
                t.addFeature(new EnablingFunction("(" + b.getName() + "==0)"));
                break;
            case DETERMINISTIC:
                for(Transition transition: transitionsToEdit) {
                    if (net.getPostconditions(transition).size() > 1) {
                        net.removePostcondition(net.getPostcondition(transition, b));
                    } else {
                        net.removePostcondition(net.getPostcondition(transition, b));
                        navigateBackAndRemove(transition);
                    }
                }
                net.addPrecondition(a, t);
                net.addPostcondition(t, b);
                break;
        }
    }

    private void navigateBackAndRemove(Transition transition){
        net.removeTransition(transition);
        for (Precondition precondition : net.getPreconditions(transition)) {
            net.removePlace(precondition.getPlace());
            net.removePrecondition(precondition);
            Transition t = net.getTransition(getTransitionName(precondition.getPlace().getName()));
            if (t != null) {
                navigateBackAndRemove(t);
            }
            //check if it's been declared a failure occurrence before and delete it
            t = net.getTransition(getTransitionName(precondition.getPlace().getName()+"Occurrence"));
            if (t != null) {
                navigateBackAndRemove(t);
            }
        }
    }

    private void decorateFailure(Failure failure, BigDecimal timestamp, PetriNetTranslatorMethod pntMethod){
        Place b = net.getPlace(failure.getFailureMode().getDescription());
        if(b != null) {
            Place a = net.addPlace(b.getName() + "Occurrence");
            marking.setTokens(a, 1);
            Transition errorModeTransition = net.getTransition(getTransitionName(b.getName()));
            Transition t = net.addTransition(getTransitionName(a.getName()));
            t.addFeature(PDFParser.parseStringToStochasticTransitionFeature("dirac(" + timestamp.toString() + ")"));
            net.addPrecondition(a, t);
            net.addPostcondition(t, b);
            switch (pntMethod) {
                case CONCURRENT:
                    //ConcurrentMode: keeps the errorMode and add FailureOccurrence
                    String enablingFunction = createEnablingFunctionToAppend(b);
                    t.addFeature(new EnablingFunction(enablingFunction));
                    String newEnablingFunction = errorModeTransition.getFeature(EnablingFunction.class).toString() + "&&" + enablingFunction;
                    errorModeTransition.removeFeature(EnablingFunction.class);
                    errorModeTransition.addFeature(new EnablingFunction(newEnablingFunction));
                    break;
                case DETERMINISTIC:
                    //DeterministicMode: deletes the errorMode and add FailureOccurrence
                    for (Precondition precondition : net.getPreconditions(errorModeTransition)) {
                        net.removePrecondition(precondition);
                        net.removePlace(precondition.getPlace());
                    }
                    net.removeTransition(errorModeTransition);
                    break;
            }
        }
    }

    private String createEnablingFunctionToAppend(Place failure){
        StringBuilder enablingFunction = new StringBuilder("((" + failure.getName() + "==0)");
        Transition t = net.getTransition(failure.getName()+"toFaults");
        for(Postcondition pc: net.getPostconditions(t)){
            enablingFunction.append("&&(").append(pc.getPlace().getName()).append("==0)");
        }
        enablingFunction.append(")");
        return enablingFunction.toString();
    }

    private void decorateError(Error error, BigDecimal timestamp){
        Transition t = net.getTransition(getTransitionName(error.getErrorMode().getOutgoingFailure().getDescription()));
        TransitionFeature tf = t.getFeature(StochasticTransitionFeature.class);
        if (tf != null)
            t.removeFeature(StochasticTransitionFeature.class);
        t.addFeature(PDFParser.parseStringToStochasticTransitionFeature("dirac("+timestamp.toString()+")"));
    }


    public PetriNet getPetriNet() {
        return net;
    }

    public Marking getMarking() {
        return marking;
    }
}
