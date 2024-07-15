package it.unifi.stlab.analysis;

import org.oristool.petrinet.Marking;
import org.oristool.petrinet.PetriNet;
import org.oristool.petrinet.Place;

import java.util.List;
import java.util.stream.Collectors;

public class PetriNetReducer {

    private final PetriNet petriNet;
    private final Marking marking;

    public PetriNetReducer(PetriNet petriNet, Marking marking) {
        this.petriNet = petriNet;
        this.marking = new Marking(marking);
    }

    public void reduce(List<String> contributingPlaces) {
        List<Place> placesWithTokens = petriNet.getPlaces()
                .stream()
                .filter(place -> marking.getTokens(place.getName()) > 0).collect(Collectors.toList());

        for (Place netPlace : placesWithTokens) {
            if (contributingPlaces.stream().noneMatch(place -> netPlace.getName().contains(place))) {
                marking.setTokens(netPlace, 0);
            }
        }
    }

    public PetriNet getPetriNet() {
        return petriNet;
    }

    public Marking getMarking() {
        return marking;
    }
}
