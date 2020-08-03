package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.*;
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
public class OrderByComponentStrategy implements ExportStrategy {
    private HashMap<String, List<PropagationPort>> failConnections;
    private HashMap<String, ErrorMode> errorModes;
    private PetriNet petriNet = null;
    private Marking marking = null;

    public OrderByComponentStrategy(HashMap<String, List<PropagationPort>> failConnections, HashMap<String, ErrorMode> errorModes,PetriNet petriNet, Marking marking){
        this.failConnections=failConnections;
        this.errorModes=errorModes;
        this.petriNet = petriNet;
        this.marking = marking;
    }
    @Override
    public TpnEditor translate(){
        ObjectFactory objectFactory = new ObjectFactory();
        TpnEditor tpnEditor = objectFactory.createTpnEditor();
        TPNEntities tpnEntities = objectFactory.createTPNEntities();
        translateOccurrences(failConnections,errorModes, petriNet, marking, tpnEntities);
        tpnEditor.setTpnEntities(tpnEntities);
        return tpnEditor;
    }
    //dovrei passsare qui dentro anche una lista di errorMode, il resto concettualmente è giusto. 
    private void translateOccurrences(HashMap<String, List<PropagationPort>> failConnections, HashMap<String, ErrorMode> errorModes, PetriNet petriNet, Marking marking, TPNEntities tpnEntities){
        int x,y;
        y=Y_START;
        HashMap<String, List<org.oristool.petrinet.Place>> componentPlaces = getOccurrencesOrderedByMetaComponent(errorModes, petriNet);
        for (Map.Entry<String, List<org.oristool.petrinet.Place>> mapElement : componentPlaces.entrySet()){

            for(org.oristool.petrinet.Place place : mapElement.getValue()){
                x=X_START;
                addPlace(tpnEntities, place, marking, x, y);

                x+=70;

                org.oristool.petrinet.Transition transition = petriNet.getTransition(PetriNetTranslator.getTransitionName(place.getName()));
                addTransition(tpnEntities, transition, x, y);

                addArc(tpnEntities, place.getName(), transition.getName());

                x+=X_SPACING;

                Postcondition postcondition = petriNet.getPostconditions(transition).iterator().next();
                Place fault = addPlace(tpnEntities, postcondition.getPlace(), marking, x, y);

                addArc(tpnEntities, transition.getName(), postcondition.getPlace().getName());

                y+=Y_SPACING;
                if(!getErrorModesFromFault(fault, errorModes).isEmpty())
                    propagateTranslate(tpnEntities, failConnections, errorModes, petriNet, marking, fault);
            }
            y+=100;
        }
    }

    private void propagateTranslate(TPNEntities tpnEntities, HashMap<String, List<PropagationPort>> failConnections, HashMap<String, ErrorMode> errorModes, PetriNet petriNet, Marking marking, Place fault) {
        int y = fault.getY();
        int xvalue=setSpacingToPreventOverlapping(failConnections);
        for(ErrorMode em : getErrorModesFromFault(fault, errorModes)){
            if(!isPlaceInXML(tpnEntities, em.getName())){
                int x = fault.getX()+xvalue;

                addPlace(tpnEntities, petriNet.getPlace(em.getName()), marking, x, y);
                x += xvalue;
                org.oristool.petrinet.Transition transition = petriNet.getTransition(PetriNetTranslator.getTransitionName(em.getOutgoingFailure().getDescription()));
                addTransition(tpnEntities, transition, x, y);

                addArc(tpnEntities, em.getName(), transition.getName());

                x += xvalue;

                Place next = addPlace(tpnEntities, petriNet.getPlace(em.getOutgoingFailure().getDescription()), marking, x, y);
                addArc(tpnEntities, transition.getName(), em.getOutgoingFailure().getDescription());
                if(failConnections.get(next.getUuid())!=null){

                    FaultMode exoFault = failConnections.get(next.getUuid()).stream().findFirst().get().getExogenousFaultMode();
                    Transition t = addTransition(tpnEntities, petriNet.getTransition(PetriNetTranslator.getTransitionName(exoFault.getName())), next.getX()+70, next.getY());
                    addArc(tpnEntities, next.getUuid(), t.getUuid());
                    //addArc(tpnEntities, PetriNetTranslator.getTransitionName(em.getOutgoingFailure().getDescription()), exoFault.getName());
                    Place b = getPlace(tpnEntities, exoFault.getName());
                    if(!isPlaceInXML(tpnEntities, exoFault.getName()))
                        b= addPlace(tpnEntities, petriNet.getPlace(exoFault.getName()), marking, t.getX()+180, t.getY());
                    else{
                        if(b.getY()!=fault.getY()) {
                            adjustPlacePosition(b, t.getX()+180, fault.getY());
                        }
                    }
                    addArc(tpnEntities, t.getUuid(), b.getUuid());
                    if(!getErrorModesFromFault(b, errorModes).isEmpty())
                        propagateTranslate(tpnEntities, failConnections,errorModes, petriNet, marking, b);
                }
                //propagateTranslate(tpnEntities, failConnections, errorModes,petriNet, marking, next);
                y+=Y_SPACING;
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

    private HashMap<String, List<org.oristool.petrinet.Place>> getOccurrencesOrderedByMetaComponent(HashMap<String, ErrorMode> errorModes, PetriNet petrinet) {
        HashMap<String, List<org.oristool.petrinet.Place>> componentPlaces = new HashMap<>();
        for (ErrorMode errorMode : errorModes.values()) {
            for(FaultMode faultMode: errorMode.getInputFaultModes()) {
                if(faultMode instanceof EndogenousFaultMode) {
                    org.oristool.petrinet.Place place = petrinet.getPlace(faultMode.getName() + "Occurrence");
                    if (componentPlaces.get(errorMode.getMetaComponent().getName()) == null)
                        componentPlaces.computeIfAbsent(errorMode.getMetaComponent().getName(), k -> new ArrayList<>()).add(place);
                    else {
                        if (!componentPlaces.get(errorMode.getMetaComponent().getName()).contains(place))
                            componentPlaces.get(errorMode.getMetaComponent().getName()).add(place);
                    }
                }
            }
        }
        return componentPlaces;
    }

    private List<ErrorMode> getErrorModesFromFault(Place fault, HashMap<String, ErrorMode> errorModes){
        return errorModes.values().stream().filter(x-> x.checkFaultIsPresent(fault.getUuid())).collect(Collectors.toList());
    }

    int setSpacingToPreventOverlapping(HashMap<String, List<PropagationPort>> failconnections){
        Set<String> keyset = failconnections.keySet();
        String maxstring = keyset.stream().max(Comparator.comparingInt(String::length)).toString();
        return maxstring.length()*5;
    }

}
