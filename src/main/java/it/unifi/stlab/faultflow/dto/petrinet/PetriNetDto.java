package it.unifi.stlab.faultflow.dto.petrinet;

import java.util.List;

public class PetriNetDto {
    private List<PlaceDto> places;
    private List<TransitionDto> transitions;
    private List<PreconditionDto> preconditions;
    private List<PostconditionDto> postconditions;

    public List<PlaceDto> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceDto> places) {
        this.places = places;
    }

    public List<TransitionDto> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<TransitionDto> transitions) {
        this.transitions = transitions;
    }

    public List<PreconditionDto> getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(List<PreconditionDto> preconditions) {
        this.preconditions = preconditions;
    }

    public List<PostconditionDto> getPostconditions() {
        return postconditions;
    }

    public void setPostconditions(List<PostconditionDto> postconditions) {
        this.postconditions = postconditions;
    }
}
