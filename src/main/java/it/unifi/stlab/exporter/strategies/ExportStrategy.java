package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import org.oristool.math.function.Erlang;
import org.oristool.math.function.GEN;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;
import org.oristool.petrinet.EnablingFunction;
import org.oristool.petrinet.Marking;

import it.unifi.stlab.exporter.jaxb.Arc;
import it.unifi.stlab.exporter.jaxb.ListPlaceProperty;
import it.unifi.stlab.exporter.jaxb.ListTransitionFeatures;
import it.unifi.stlab.exporter.jaxb.ListTransitionProperty;
import it.unifi.stlab.exporter.jaxb.ObjectFactory;
import it.unifi.stlab.exporter.jaxb.PlaceProperty;
import it.unifi.stlab.exporter.jaxb.TPNEntities;
import it.unifi.stlab.exporter.jaxb.TpnEditor;
import it.unifi.stlab.exporter.jaxb.Transition;
import it.unifi.stlab.exporter.jaxb.TransitionFeature;
import it.unifi.stlab.exporter.jaxb.TransitionProperty;

import java.util.*;

/**
 * Interface that implements some useful methods to give a representation of the Model into an xml file that represent a PetriNet.
 */
public interface ExportStrategy {
    int Y_SPACING = 75;
    int X_SPACING = 180;
    int X_START=75;
    int Y_START=100;

    TpnEditor translate();

    default Arc addArc(TPNEntities tpnEntities, String from, String to) {
        ObjectFactory objectFactory = new ObjectFactory();
        Arc arc = objectFactory.createArc();
        arc.setFrom(from);
        arc.setTo(to);
        tpnEntities.getArc().add(arc);
        return arc;
    }

    default Transition addTransition(TPNEntities tpnEntities, org.oristool.petrinet.Transition transition, int x, int y) {
        ObjectFactory objectFactory = new ObjectFactory();
        Transition t = objectFactory.createTransition();
        t.setUuid(transition.getName());
        t.setX(x);
        t.setY(y);
        t.setProperties(makeTransitionProperties(t, transition));
        t.setFeatures(makeTransitionFeature(t));
        tpnEntities.getTransition().add(t);
        return t;
    }

    default it.unifi.stlab.exporter.jaxb.Place addPlace(TPNEntities tpnEntities, org.oristool.petrinet.Place place, Marking marking, int x, int y){
        ObjectFactory objectFactory = new ObjectFactory();
        it.unifi.stlab.exporter.jaxb.Place p = objectFactory.createPlace();
        p.setUuid(place.getName());
        p.setX(x);
        p.setY(y);
        p.setProperties(makePlaceProperties(p, place, marking));
        tpnEntities.getPlace().add(p);
        return p;

    }

    default boolean isPlaceInXML(TPNEntities tpnEntities, String uuid){
        return getPlace(tpnEntities, uuid) != null;
    }
    default it.unifi.stlab.exporter.jaxb.Place getPlace(TPNEntities tpnEntities, String uuid){
        return tpnEntities.getPlace().stream()
                .filter(place -> uuid.equals(place.getUuid()))
                .findAny()
                .orElse(null);
    }
    default boolean isTransitionInXML(TPNEntities tpnEntities, String uuid){
        return getTransition(tpnEntities, uuid) != null;
    }
    default Transition getTransition(TPNEntities tpnEntities, String uuid){
        return tpnEntities.getTransition().stream()
                .filter(transition -> uuid.equals(transition.getUuid()))
                .findAny()
                .orElse(null);
    }
    default void adjustPlacePosition(it.unifi.stlab.exporter.jaxb.Place place, int newX, int newY){
        place.setX(newX);
        place.setY(newY);
        PlaceProperty name = place.getProperties().getProperty().stream()
                .filter(property -> property.getId().equals("0.default.name"))
                .findAny()
                .orElse(null);
        if(name!= null){
            place.getProperties().getProperty().remove(name);
            place.getProperties().getProperty().add(makePlaceName(place));
        }
    }

    default void adjustTransitionPosition(Transition transition, int newX, int newY){
        transition.setX(newX);
        transition.setY(newY);
        TransitionProperty name = transition.getProperties().getProperty().stream()
                .filter(property -> property.getId().equals("0.default.name"))
                .findAny()
                .orElse(null);
        if(name!= null){
            transition.getProperties().getProperty().remove(name);
            transition.getProperties().getProperty().add(makeTransitionName(transition));
        }
    }

    default ListTransitionFeatures makeTransitionFeature(Transition transition){
        ObjectFactory objectFactory = new ObjectFactory();
        ListTransitionFeatures transitionFeatures = objectFactory.createListTransitionFeatures();
        TransitionFeature transitionFeature = objectFactory.createTransitionFeature();
        transitionFeature.setId("transition.stochastic");
        transitionFeatures.setFeature(transitionFeature);
        return transitionFeatures;
    }
    default ListTransitionProperty makeTransitionProperties(Transition transition, org.oristool.petrinet.Transition petriNetTransition){
        ObjectFactory objectFactory = new ObjectFactory();
        ListTransitionProperty transitionPropertyList = objectFactory.createListTransitionProperty();
        transitionPropertyList.getProperty().add(makeTransitionName(transition));
        transitionPropertyList.getProperty().add(makeTransitionEnablingFunction(petriNetTransition));
        transitionPropertyList.getProperty().add(makeTransitionCDF(transition,petriNetTransition));
        return transitionPropertyList;

    }
    default TransitionProperty makeTransitionCDF(Transition transition, org.oristool.petrinet.Transition petriNetTransition){
        //In this particular case only Erlang, Immediate and Deterministic CDF are supported
        //TODO: add xml translation for other types of CDF too.
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionProperty stochastic = objectFactory.createTransitionProperty();
        stochastic.setId("transition.stochastic");
        StochasticTransitionFeature stochasticTransitionFeature = petriNetTransition.getFeature(StochasticTransitionFeature.class);
        if(Erlang.class.equals(stochasticTransitionFeature.density().getClass())){
            stochastic.setK(((Erlang) stochasticTransitionFeature.density()).getShape());
            stochastic.setLambda(((Erlang) stochasticTransitionFeature.density()).getLambda().intValue());
            stochastic.setPropertyDataType("4.type.erlang");
        }
        else if(GEN.class.equals(stochasticTransitionFeature.density().getClass())){
            String domain = ((GEN) stochasticTransitionFeature.density()).getDomain().toString();
            int fireTime = Integer.parseInt(domain.substring(0,domain.indexOf(" ")));
            if(fireTime==0){
                stochastic.setPropertyDataType("0.type.immediate");
            }
            else{
                stochastic.setPropertyDataType("2.type.deterministic");
                stochastic.setValue(fireTime);
            }
            stochastic.setWeight(1);
        }
        else
            throw new UnsupportedOperationException("This type of StochasticTransitionFeature is unsupported");
        stochastic.setSatelliteX(transition.getX()+20);
        stochastic.setSatelliteY(transition.getY()+30);
        return stochastic;
    }
    default TransitionProperty makeTransitionEnablingFunction(org.oristool.petrinet.Transition petriNetTransition){
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionProperty enablingFunction = objectFactory.createTransitionProperty();
        enablingFunction.setId("10.default.enablingFunction");
        EnablingFunction eF = petriNetTransition.getFeature(EnablingFunction.class);
        if(eF!=null){
            enablingFunction.setEnablingFunction(eF.toString());
        }
        return enablingFunction;
    }
    default TransitionProperty makeTransitionName(Transition transition){
        ObjectFactory objectFactory = new ObjectFactory();
        TransitionProperty name = objectFactory.createTransitionProperty();
        name.setId("0.default.name");
        name.setName(transition.getUuid());
        name.setSatelliteX(transition.getX()+70);
        name.setSatelliteY(transition.getY()-25);
        return name;
    }
    default ListPlaceProperty makePlaceProperties(it.unifi.stlab.exporter.jaxb.Place place, org.oristool.petrinet.Place petriNetPlace, Marking marking){
        ObjectFactory objectFactory = new ObjectFactory();
        ListPlaceProperty placePropertyList = objectFactory.createListPlaceProperty();
        placePropertyList.getProperty().add(makePlaceName(place));
        placePropertyList.getProperty().add(makeMarking(petriNetPlace, marking));
        return placePropertyList;

    }
    default PlaceProperty makePlaceName(it.unifi.stlab.exporter.jaxb.Place place){
        ObjectFactory objectFactory = new ObjectFactory();
        PlaceProperty name = objectFactory.createPlaceProperty();
        name.setId("0.default.name");
        name.setName(place.getUuid());
        name.setSatelliteX(place.getX()+10);
        name.setSatelliteY(place.getY()+25);
        return name;
    }
    default PlaceProperty makeMarking(org.oristool.petrinet.Place place, Marking marking){
        ObjectFactory objectFactory = new ObjectFactory();
        PlaceProperty marks = objectFactory.createPlaceProperty();
        marks.setId("default.marking");
        marks.setMarking(marking.getTokens(place));
        return marks;
    }
}
