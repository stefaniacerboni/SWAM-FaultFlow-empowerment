package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.*;
import it.unifi.stlab.fault2failure.knowledge.composition.MetaComponent;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.FaultMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import org.oristool.petrinet.Marking;
import org.oristool.petrinet.PetriNet;
import org.oristool.petrinet.Postcondition;

import java.util.*;
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
        HashMap<MetaComponent, List<org.oristool.petrinet.Place>> componentPlaces = getOccurrencesOrderedByMetaComponent(system, petriNet);
        for (Map.Entry<MetaComponent, List<org.oristool.petrinet.Place>> mapElement : componentPlaces.entrySet()) {

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

    private void propagateTranslate(TPNEntities tpnEntities, MetaComponent metaComponent, PetriNet petriNet, Marking marking, Place fault) {
        int y = fault.getY();
        int xvalue = 200;
        for (ErrorMode em : getErrorModesFromFault(fault, metaComponent)) {
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
                List<PropagationPort> propagationPorts = metaComponent.getPropagationPorts().stream().filter(pp -> pp.getPropagatedFailureMode().getDescription().equals(next.getUuid())).collect(Collectors.toList());
                if (!propagationPorts.isEmpty()) {
                    for (PropagationPort propagationPort : propagationPorts) {
                        FaultMode exoFault = propagationPort.getExogenousFaultMode();
                        String transitionName = propagationPort.getPropagatedFailureMode().getDescription()+"toFaults";
                        Transition t = getTransition(tpnEntities, transitionName);
                        if(t==null) {
                            t = addTransition(tpnEntities, petriNet.getTransition(propagationPort.getPropagatedFailureMode().getDescription() + "toFaults"), next.getX() + 70, next.getY());
                            addArc(tpnEntities, next.getUuid(), t.getUuid());
                        }
                        //addArc(tpnEntities, PetriNetTranslator.getTransitionName(em.getOutgoingFailure().getDescription()), exoFault.getName());
                        Place b = getPlace(tpnEntities, exoFault.getName());
                        if (!isPlaceInXML(tpnEntities, exoFault.getName()))
                            b = addPlace(tpnEntities, petriNet.getPlace(exoFault.getName()), marking, t.getX() + 180, t.getY());
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
                //propagateTranslate(tpnEntities, failConnections, errorModes,petriNet, marking, next);
                y += Y_SPACING;
            }
        }

        /*
        int y = fault.getY();
        int xvalue=setSpacingToPreventOverlapping(failConnections);
        for(PropagationPort pp: failConnections.get(fault.getUuid())){
            if(pp.getErrorMode() !=null && !isPlaceInXML(tpnEntities, pp.getErrorMode().getName())){
                int x = fault.getX()+xvalue;

                addPlace(tpnEntities, petriNet.getPlace(pp.getErrorMode().getName()), marking, x, y);
                x += xvalue;
                org.oristool.petrinet.Transition transition = petriNet.getTransition(PetriNetTranslator.getTransitionName(pp.getErrorMode().getOutgoingFailure().getDescription()));
                addTransition(tpnEntities, transition, x, y);

                addArc(tpnEntities, pp.getErrorMode().getName(), transition.getName());

                x += xvalue;

                Place next = addPlace(tpnEntities, petriNet.getPlace(pp.getFailOut().getDescription()), marking, x, y);
                addArc(tpnEntities, transition.getName(), pp.getFailOut().getDescription());
                if(failConnections.get(next.getUuid())!=null)
                    propagateTranslate(tpnEntities, failConnections, petriNet, marking, next);
                y+=Y_SPACING;
            }
            else if(pp.getErrorMode()==null){
                Transition t = addTransition(tpnEntities, petriNet.getTransition(PetriNetTranslator.getTransitionName(pp.getFailOut().getDescription())), fault.getX()+70, fault.getY());
                addArc(tpnEntities, fault.getUuid(), PetriNetTranslator.getTransitionName(pp.getFailOut().getDescription()));
                Place b = getPlace(tpnEntities, pp.getFailOut().getDescription());
                if(!isPlaceInXML(tpnEntities, pp.getFailOut().getDescription()))
                    b= addPlace(tpnEntities, petriNet.getPlace(pp.getFailOut().getDescription()), marking, t.getX()+180, t.getY());
                else{
                    if(b.getY()!=fault.getY()) {
                        adjustPlacePosition(b, t.getX()+180, fault.getY());
                    }
                }
                addArc(tpnEntities, t.getUuid(), b.getUuid());
                if(failConnections.get(b.getUuid())!=null)
                    propagateTranslate(tpnEntities, failConnections, petriNet, marking, b);
            }
        }
        */
    }
    /*
    private HashMap<String, List<org.oristool.petrinet.Place>> getOccurrencesOrderedByMetaComponent(HashMap<String, List<PropagationPort>> propagationPorts, PetriNet petrinet) {
        HashMap<String, List<org.oristool.petrinet.Place>> componentPlaces = new HashMap<>();
        List<org.oristool.petrinet.Place> occurrences = new ArrayList<>();
        for (org.oristool.petrinet.Place place : petrinet.getPlaces()) {
            if (place.getName().endsWith("Occurrence")) {
                occurrences.add(place);
            }
        }
        for (org.oristool.petrinet.Place place : occurrences) {
            List<PropagationPort> faultPropagationPorts = propagationPorts.get(place.getName().replace("Occurrence", ""));
            for(PropagationPort pp: faultPropagationPorts) {
                if(componentPlaces.get(pp.getMetaComponent().getName())==null)
                    componentPlaces.computeIfAbsent(pp.getMetaComponent().getName(), k -> new ArrayList<>()).add(place);
                else{
                    if(!componentPlaces.get(pp.getMetaComponent().getName()).contains(place))
                        componentPlaces.get(pp.getMetaComponent().getName()).add(place);
                }
            }
        }
        return componentPlaces;
    }

     */

    private HashMap<MetaComponent, List<org.oristool.petrinet.Place>> getOccurrencesOrderedByMetaComponent(System system, PetriNet petrinet) {
        HashMap<MetaComponent, List<org.oristool.petrinet.Place>> componentPlaces = new HashMap<>();
        for (MetaComponent metaComponent : system.getComponents()) {
            for (ErrorMode errorMode : metaComponent.getErrorModes()) {
                for (FaultMode faultMode : errorMode.getInputFaultModes()) {
                    if (faultMode instanceof EndogenousFaultMode) {
                        org.oristool.petrinet.Place place = petrinet.getPlace(faultMode.getName() + "Occurrence");
                        if (componentPlaces.get(metaComponent) == null)
                            componentPlaces.computeIfAbsent(metaComponent, k -> new ArrayList<>()).add(place);
                        else {
                            if (!componentPlaces.get(metaComponent).contains(place))
                                componentPlaces.get(metaComponent).add(place);
                        }
                    }
                }
            }
        }
        return componentPlaces;
    }

    private List<ErrorMode> getErrorModesFromFault(Place fault, MetaComponent metaComponent) {
        return metaComponent.getErrorModes().stream().filter(x -> x.checkFaultIsPresent(fault.getUuid())).collect(Collectors.toList());
    }

    int setSpacingToPreventOverlapping(HashMap<String, List<PropagationPort>> failconnections) {
        Set<String> keyset = failconnections.keySet();
        String maxstring = keyset.stream().max(Comparator.comparingInt(String::length)).toString();
        return maxstring.length() * 5;
    }

}
