package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.*;
import it.unifi.stlab.fault2failure.knowledge.composition.Component;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import org.oristool.petrinet.Marking;
import org.oristool.petrinet.PetriNet;
import org.oristool.petrinet.Postcondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create an export strategy that implements and order between places and transition.
 * Places that represent Failures or ErrorMode that works or happen in the same Component will appear near to each other
 * and far from other places that affect other Components.
 * Also as the BasicExportStrategy do, places and transition connected to each other will appear aligned horizontally (with the same y coordinate)
 */
public class OrderByComponentToXPN implements ExportToXPN {
    private final System system;
    private PetriNet petriNet;
    private Marking marking;

    public OrderByComponentToXPN(System system, PetriNet petriNet, Marking marking) {
        this.system = system;
        this.petriNet = petriNet;
        this.marking = marking;
    }

    @Override
    public TpnEditor translate() {
        ObjectFactory objectFactory = new ObjectFactory();
        TpnEditor tpnEditor = objectFactory.createTpnEditor();
        TPNEntities tpnEntities = objectFactory.createTPNEntities();
        translateOccurrences(system, petriNet, marking, tpnEntities);
        tpnEditor.setTpnEntities(tpnEntities);
        return tpnEditor;
    }

    private void translateOccurrences(System system, PetriNet petriNet, Marking marking, TPNEntities tpnEntities) {
        int x, y;
        y = Y_START;
        HashMap<Component, List<org.oristool.petrinet.Place>> componentPlaces = getOccurrencesOrderedByMetaComponent(system, petriNet);
        for (Map.Entry<Component, List<org.oristool.petrinet.Place>> mapElement : componentPlaces.entrySet()) {

            for (org.oristool.petrinet.Place place : mapElement.getValue()) {
                x = X_START;
                addPlace(tpnEntities, place, marking, x, y);

                x += 70;

                org.oristool.petrinet.Transition transition = petriNet.getTransition(PetriNetTranslator.getTransitionName(place.getName()));
                addTransition(tpnEntities, transition, x, y);

                addArc(tpnEntities, place.getName(), transition.getName());

                x += X_SPACING;

                Postcondition postcondition = petriNet.getPostconditions(transition).iterator().next();
                Place fault = addPlace(tpnEntities, postcondition.getPlace(), marking, x, y);

                addArc(tpnEntities, transition.getName(), postcondition.getPlace().getName());

                y += Y_SPACING;
                if (!getErrorModesFromFault(fault, mapElement.getKey()).isEmpty())
                    propagateTranslate(tpnEntities, mapElement.getKey(), petriNet, marking, fault);
            }
            y += 100;
        }
    }

    private void propagateTranslate(TPNEntities tpnEntities, Component component, PetriNet petriNet, Marking marking, Place fault) {
        int y = fault.getY();
        int xvalue = 200;
        for (ErrorMode em : getErrorModesFromFault(fault, component)) {
            if (!isPlaceInXML(tpnEntities, em.getName())) {
                int x = fault.getX() + xvalue;

                addPlace(tpnEntities, petriNet.getPlace(em.getName()), marking, x, y);
                x += xvalue;
                org.oristool.petrinet.Transition transition = petriNet.getTransition(PetriNetTranslator.getTransitionName(em.getOutgoingFailure().getDescription()));
                addTransition(tpnEntities, transition, x, y);

                addArc(tpnEntities, em.getName(), transition.getName());

                x += xvalue;

                Place next = addPlace(tpnEntities, petriNet.getPlace(em.getOutgoingFailure().getDescription()), marking, x, y);
                addArc(tpnEntities, transition.getName(), em.getOutgoingFailure().getDescription());
                List<PropagationPort> propagationPorts = component.getPropagationPorts().stream().filter(pp -> pp.getPropagatedFailureMode().getDescription().equals(next.getUuid())).collect(Collectors.toList());
                if (!propagationPorts.isEmpty()) {
                    int i = 0;
                    for (PropagationPort propagationPort : propagationPorts) {
                        FaultMode exoFault = propagationPort.getExogenousFaultMode();
                        String transitionName = propagationPort.getPropagatedFailureMode().getDescription()+"toFaults";
                        Transition t = getTransition(tpnEntities, transitionName);
                        if(t==null) {
                            t = addTransition(tpnEntities, petriNet.getTransition(propagationPort.getPropagatedFailureMode().getDescription() + "toFaults"), next.getX() + 70, next.getY());
                            addArc(tpnEntities, next.getUuid(), t.getUuid());
                        }
                        Place b = getPlace(tpnEntities, exoFault.getName());
                        if (!isPlaceInXML(tpnEntities, exoFault.getName())) {
                            b = addPlace(tpnEntities, petriNet.getPlace(exoFault.getName()), marking, t.getX() + 180,
                                    (t.getY()+(((-1)^i)*30*((i+1)/2))));
                            i++;
                        }
                        else {
                            if (b.getY() != fault.getY()) {
                                adjustPlacePosition(b, t.getX() + 180, fault.getY());
                            }
                        }
                        addArc(tpnEntities, t.getUuid(), b.getUuid());
                        if (!getErrorModesFromFault(b, propagationPort.getAffectedComponent()).isEmpty())
                            propagateTranslate(tpnEntities, propagationPort.getAffectedComponent(), petriNet, marking, b);

                    }
                }
                y += Y_SPACING;
            }
        }
    }

    private HashMap<Component, List<org.oristool.petrinet.Place>> getOccurrencesOrderedByMetaComponent(System system, PetriNet petrinet) {
        HashMap<Component, List<org.oristool.petrinet.Place>> componentPlaces = new HashMap<>();
        for (Component component : system.getComponents()) {
            for (ErrorMode errorMode : component.getErrorModes()) {
                for (FaultMode faultMode : errorMode.getInputFaultModes()) {
                    org.oristool.petrinet.Place place = petrinet.getPlace(faultMode.getName() + "Occurrence");
                    if (place != null) {
                        if (componentPlaces.get(component) == null)
                            componentPlaces.computeIfAbsent(component, k -> new ArrayList<>()).add(place);
                        else {
                            if (!componentPlaces.get(component).contains(place))
                                componentPlaces.get(component).add(place);
                        }
                    }
                }
                //check failures too
                org.oristool.petrinet.Place place = petrinet.getPlace(errorMode.getOutgoingFailure().getDescription() + "Occurrence");
                if (place != null) {
                    if (componentPlaces.get(component) == null)
                        componentPlaces.computeIfAbsent(component, k -> new ArrayList<>()).add(place);
                    else {
                        if (!componentPlaces.get(component).contains(place))
                            componentPlaces.get(component).add(place);
                    }
                }
            }
        }
        return componentPlaces;
    }

    private List<ErrorMode> getErrorModesFromFault(Place fault, Component component) {
        return component.getErrorModes().stream().filter(x -> x.checkFaultIsPresent(fault.getUuid())).collect(Collectors.toList());
    }
/*
This was formerly used to prevent overlapping of transitions' and places' labels

    int setSpacingToPreventOverlapping(HashMap<String, List<PropagationPort>> failconnections) {
        Set<String> keyset = failconnections.keySet();
        String maxstring = keyset.stream().max(Comparator.comparingInt(String::length)).toString();
        return maxstring.length() * 5;
    }

 */

}
