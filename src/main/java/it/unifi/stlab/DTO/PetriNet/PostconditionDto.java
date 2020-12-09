package it.unifi.stlab.DTO.PetriNet;

public class PostconditionDto {
    private String transition;
    private String place;

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
