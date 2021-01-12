package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.*;
import org.oristool.petrinet.Marking;
import org.oristool.petrinet.PetriNet;
import org.oristool.petrinet.Postcondition;
import org.oristool.petrinet.Precondition;

/**
 * Create an export strategy "basic", this means without any particular order in places and transition.
 * The only constraint is that Places and Transition connected together will appear aligned horizontally (with the same y coordinate)
 */
public class BasicExportToXPN implements ExportToXPN {
    private PetriNet petriNet;
    private Marking marking;

    public BasicExportToXPN(PetriNet petriNet, Marking marking) {
        this.petriNet = petriNet;
        this.marking = marking;
    }

    @Override
    public TpnEditor translate() {
        ObjectFactory objectFactory = new ObjectFactory();
        TpnEditor tpnEditor = objectFactory.createTpnEditor();
        TPNEntities tpnEntities = objectFactory.createTPNEntities();
        int x, y;
        y = Y_START;
        for (org.oristool.petrinet.Transition transition : petriNet.getTransitions()) {
            x = X_START;
            Transition t;
            if (!isTransitionInXML(tpnEntities, transition.getName()))
                t = addTransition(tpnEntities, transition, x + 150, y);
            else
                t = getTransition(tpnEntities, transition.getName());
            for (Precondition precondition : petriNet.getPreconditions(transition)) {
                Place from;
                if (!isPlaceInXML(tpnEntities, precondition.getPlace().getName())) {
                    from = addPlace(tpnEntities, precondition.getPlace(), marking, x, t.getY());
                } else {
                    from = getPlace(tpnEntities, precondition.getPlace().getName());
                    if (from.getY() != t.getY()) {
                        adjustTransitionPosition(t, from.getX() + 150, from.getY());
                        y -= 60;
                    }
                }
                addArc(tpnEntities, from.getUuid(), t.getUuid());
            }
            int i=0;
            for (Postcondition postcondition : petriNet.getPostconditions(transition)) {
                Place to;
                if (!isPlaceInXML(tpnEntities, postcondition.getPlace().getName())) {
                    //yPos, calculated with index i to prevent overlapping of multiple places coming out from the same transition
                    int yPos = t.getY()+(((-1)^i)*30*((i+1)/2));
                    to = addPlace(tpnEntities, postcondition.getPlace(), marking, t.getX() + 150,
                            yPos);
                    i++;

                } else {
                    to = getPlace(tpnEntities, postcondition.getPlace().getName());
                }
                addArc(tpnEntities, t.getUuid(), to.getUuid());
            }
            y += Y_SPACING;

        }
        tpnEditor.setTpnEntities(tpnEntities);
        return tpnEditor;
    }
}
