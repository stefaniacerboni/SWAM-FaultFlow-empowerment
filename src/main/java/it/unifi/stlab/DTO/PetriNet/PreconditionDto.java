package it.unifi.stlab.DTO.PetriNet;

public class PreconditionDto {
    private String place;
    private String transition;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }
}
