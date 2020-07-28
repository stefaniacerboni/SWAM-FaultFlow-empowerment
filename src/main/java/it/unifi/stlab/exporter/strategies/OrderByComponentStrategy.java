package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.*;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import org.oristool.petrinet.Marking;
import org.oristool.petrinet.PetriNet;
import org.oristool.petrinet.Postcondition;

import java.util.*;

/**
 * Create an export strategy that implements and order between places and transition.
 * Places that represent Failures or ErrorMode that works or happen in the same Component will appear near to each other
 * and far from other places that affect other Components.
 * Also as the BasicExportStrategy do, places and transition connected to each other will appear aligned horizontally (with the same y coordinate)
 */
public class OrderByComponentStrategy implements ExportStrategy {
    private HashMap<String, List<PropagationPort>> failConnections;
    private PetriNet petriNet = null;
    private Marking marking = null;

    public OrderByComponentStrategy(HashMap<String, List<PropagationPort>> failConnections, PetriNet petriNet, Marking marking){
        this.failConnections=failConnections;
        this.petriNet = petriNet;
        this.marking = marking;
    }
    @Override
    public TpnEditor translate(){
        ObjectFactory objectFactory = new ObjectFactory();
        TpnEditor tpnEditor = objectFactory.createTpnEditor();
        TPNEntities tpnEntities = objectFactory.createTPNEntities();
        translateOccurrences(failConnections, petriNet, marking, tpnEntities);
        tpnEditor.setTpnEntities(tpnEntities);
        return tpnEditor;
    }
    private void translateOccurrences(HashMap<String, List<PropagationPort>> failConnections, PetriNet petriNet, Marking marking, TPNEntities tpnEntities){
        int x,y;
        y=Y_START;
        HashMap<String, List<org.oristool.petrinet.Place>> componentPlaces = getOccurrencesOrderedByMetaComponent(failConnections, petriNet);
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
                if(failConnections.get(fault.getUuid())!=null)
                    propagateTranslate(tpnEntities, failConnections, petriNet, marking, fault);
            }
            y+=100;
        }
    }

    private void propagateTranslate(TPNEntities tpnEntities, HashMap<String, List<PropagationPort>> failConnections, PetriNet petriNet, Marking marking, Place fault) {
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
    }
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

    int setSpacingToPreventOverlapping(HashMap<String, List<PropagationPort>> failconnections){
        Set<String> keyset = failconnections.keySet();
        String maxstring = keyset.stream().max(Comparator.comparingInt(String::length)).toString();
        return maxstring.length()*5;
    }
}
