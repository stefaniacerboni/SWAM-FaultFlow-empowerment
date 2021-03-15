package it.unifi.stlab.faultflow.exporter.strategies;

import it.unifi.stlab.faultflow.exporter.jaxb.PNML.*;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.*;

import java.math.BigDecimal;

public class ExportToPNML implements ExportStrategy {
    private PetriNet petriNet;
    private Marking marking;
    int Y_SPACING = 75;
    int X_SPACING = 180;
    int X_START = 75;
    int Y_START = 100;

    public ExportToPNML(PetriNet petriNet, Marking marking) {
        this.petriNet = petriNet;
        this.marking = marking;
    }

    ArcType addArc(NetType net, NodeType from, NodeType to) {
        ObjectFactory objectFactory = new ObjectFactory();
        ArcType arc = objectFactory.createArcType();
        arc.setId(from.getId()+to.getId());
        arc.setSource(from);
        arc.setTarget(to);
        net.getArc().add(arc);
        return arc;
    }

    TransitionType addTransition(NetType net, org.oristool.petrinet.Transition transition, int x, int y) {
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionType t = objectFactory.createTransitionType();
        t.setId(transition.getName());
        GraphicsNodeType gnt = objectFactory.createGraphicsNodeType();
        gnt.setPosition(setPosition(x,y));
        t.setGraphics(gnt);
        //makeTransitionProperties(t, transition);
        net.getTransition().add(t);
        return t;
    }
    PositionType setPosition(int x, int y){
        ObjectFactory objectFactory = new ObjectFactory();
        PositionType positionType = objectFactory.createPositionType();
        positionType.setX(BigDecimal.valueOf(x));
        positionType.setY(BigDecimal.valueOf(y));
        return positionType;
    }

    PlaceType addPlace(NetType net, org.oristool.petrinet.Place place, Marking marking, int x, int y) {
        ObjectFactory objectFactory = new ObjectFactory();
        PlaceType p = objectFactory.createPlaceType();
        p.setId(place.getName());
        GraphicsNodeType gnt = objectFactory.createGraphicsNodeType();
        gnt.setPosition(setPosition(x,y));
        p.setGraphics(gnt);
        NodeNameType nnt = objectFactory.createNodeNameType();
        nnt.setText(place.getName());
        p.setName(nnt);
        PlaceType.InitialMarking im = objectFactory.createPlaceTypeInitialMarking();
        im.setText(""+marking.getTokens(place));
        p.setInitialMarking(im);
        net.getPlace().add(p);
        return p;

    }

    boolean isPlaceInXML(NetType net, String uuid) {
        return getPlace(net, uuid) != null;
    }

    PlaceType getPlace(NetType net, String uuid) {
        return net.getPlace().stream()
                .filter(place -> uuid.equals(place.getId()))
                .findAny()
                .orElse(null);
    }

    boolean isTransitionInXML(NetType net, String uuid) {
        return getTransition(net, uuid) != null;
    }

    TransitionType getTransition(NetType net, String uuid) {
        return net.getTransition().stream()
                .filter(transition -> uuid.equals(transition.getId()))
                .findAny()
                .orElse(null);
    }

    void adjustPlacePosition(PlaceType place, int newX, int newY) {
        place.getGraphics().getPosition().setX(BigDecimal.valueOf(newX));
        place.getGraphics().getPosition().setX(BigDecimal.valueOf(newY));
    }


    void adjustTransitionPosition(TransitionType transition, int newX, int newY) {
        transition.getGraphics().getPosition().setX(BigDecimal.valueOf(newX));
        transition.getGraphics().getPosition().setX(BigDecimal.valueOf(newY));
    }
    /*
    default TransitionToolspecificType makeTransitionFeature(TransitionType transition) {
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionToolspecificType ttst = objectFactory.createTransitionToolspecificType();
        TransitionResourceType transitionResourceType = objectFactory.createTransitionResourceType();
        transitionResourceType.setRoleName("transition.stochastic");
        transitionResourceType.setOrganizationalUnitName(tran);
        transitionFeature.setId("transition.stochastic");
        transitionFeatures.setFeature(transitionFeature);
        return transitionFeatures;
    }
**/
    void makeTransitionProperties(TransitionType transition, org.oristool.petrinet.Transition petriNetTransition) {
        transition.getToolspecific().add(makeTransitionEnablingFunction(petriNetTransition));
        transition.getToolspecific().add(makeTransitionCDF(transition, petriNetTransition));
    }

    private TransitionToolspecificType makeTransitionEnablingFunction(Transition petriNetTransition) {
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionToolspecificType ttst = objectFactory.createTransitionToolspecificType();
        TransitionToolspecificType.TransitionResource transitionResourceType = objectFactory.createTransitionToolspecificTypeTransitionResource();
        transitionResourceType.setOrganizationalUnitName("default.enablingFunction");
        EnablingFunction eF = petriNetTransition.getFeature(EnablingFunction.class);
        if (eF != null) {
            transitionResourceType.setRoleName(eF.toString());
        }
        GraphicsSimpleType gst = objectFactory.createGraphicsSimpleType();
        transitionResourceType.setGraphics(gst);
        ttst.setTransitionResource(transitionResourceType);
        return ttst;
    }

    private TransitionToolspecificType makeTransitionCDF(TransitionType transition, org.oristool.petrinet.Transition petriNetTransition) {
        //In this particular case only Erlang, Immediate and Deterministic CDF are supported
        //TODO: add xml translation for other types of CDF too.
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionToolspecificType ttst = objectFactory.createTransitionToolspecificType();
        TransitionToolspecificType.TransitionResource transitionResourceType = objectFactory.createTransitionToolspecificTypeTransitionResource();
        transitionResourceType.setOrganizationalUnitName("transition.stochastic");
        StochasticTransitionFeature stochasticTransitionFeature = petriNetTransition.getFeature(StochasticTransitionFeature.class);
        transitionResourceType.setRoleName(stochasticTransitionFeature.toString());
        GraphicsSimpleType gst = objectFactory.createGraphicsSimpleType();
        transitionResourceType.setGraphics(gst);
        ttst.setTransitionResource(transitionResourceType);
        return ttst;
    }
/*
    default TransitionProperty makeTransitionEnablingFunction(org.oristool.petrinet.Transition petriNetTransition) {
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionProperty enablingFunction = objectFactory.createTransitionProperty();
        enablingFunction.setId("10.default.enablingFunction");
        EnablingFunction eF = petriNetTransition.getFeature(EnablingFunction.class);
        if (eF != null) {
            enablingFunction.setEnablingFunction(eF.toString());
        }
        return enablingFunction;
    }

    default TransitionProperty makeTransitionName(Transition transition) {
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionProperty name = objectFactory.createTransitionProperty();
        name.setId("0.default.name");
        name.setName(transition.getUuid());
        name.setSatelliteX(transition.getX() + 70);
        name.setSatelliteY(transition.getY() - 25);
        return name;
    }

    PlaceProperty makePlaceProperties(it.unifi.stlab.exporter.jaxb.Place place, org.oristool.petrinet.Place petriNetPlace, Marking marking) {
        ObjectFactory objectFactory = new ObjectFactory();
        ListPlaceProperty placePropertyList = objectFactory.createListPlaceProperty();
        placePropertyList.getProperty().add(makePlaceName(place));
        placePropertyList.getProperty().add(makeMarking(petriNetPlace, marking));
        return placePropertyList;

    }

    default PlaceProperty makePlaceName(it.unifi.stlab.exporter.jaxb.Place place) {
        ObjectFactory objectFactory = new ObjectFactory();
        PlaceProperty name = objectFactory.createPlaceProperty();
        name.setId("0.default.name");
        name.setName(place.getUuid());
        name.setSatelliteX(place.getX() + 10);
        name.setSatelliteY(place.getY() + 25);
        return name;
    }



    PlaceProperty.Ini makeMarking(org.oristool.petrinet.Place place, Marking marking) {
        ObjectFactory objectFactory = new ObjectFactory();
        PlaceProperty marks = objectFactory.createPlaceProperty();
        marks.setId("default.marking");
        marks.setMarking(marking.getTokens(place));
        return marks;
    }

     */


    @Override
    public PnmlType translate() {

        ObjectFactory objectFactory = new ObjectFactory();
        PnmlType pnmlType = objectFactory.createPnmlType();
        NetType netType = objectFactory.createNetType();
        netType.setId("net1");
        netType.setType("Basic");
        int x, y;
        y = Y_START;
        for (org.oristool.petrinet.Transition transition : petriNet.getTransitions()) {
            x = X_START;
            TransitionType t;
            if (!isTransitionInXML(netType, transition.getName()))
                t = addTransition(netType, transition, x + 150, y);
            else
                t = getTransition(netType, transition.getName());
            for (Precondition precondition : petriNet.getPreconditions(transition)) {
                PlaceType from;
                if (!isPlaceInXML(netType, precondition.getPlace().getName())) {
                    from = addPlace(netType, precondition.getPlace(), marking, x, t.getGraphics().getPosition().getY().intValue());
                } else {
                    from = getPlace(netType, precondition.getPlace().getName());
                    if (from.getGraphics().getPosition().getY() != t.getGraphics().getPosition().getY()) {
                        adjustTransitionPosition(t, from.getGraphics().getPosition().getX().intValue() + 150, from.getGraphics().getPosition().getX().intValue());
                        y -= 60;
                    }
                }
                addArc(netType, from, t);
            }
            for (Postcondition postcondition : petriNet.getPostconditions(transition)) {
                PlaceType to;
                if (!isPlaceInXML(netType, postcondition.getPlace().getName())) {
                    to = addPlace(netType, postcondition.getPlace(), marking, t.getGraphics().getPosition().getX().intValue() + 150, t.getGraphics().getPosition().getY().intValue());

                } else {
                    to = getPlace(netType, postcondition.getPlace().getName());
                }
                addArc(netType, t, to);
            }
            y += Y_SPACING;

        }
        pnmlType.getNet().add(netType);
        return pnmlType;
    }
}
