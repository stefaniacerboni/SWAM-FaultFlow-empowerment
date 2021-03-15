package it.unifi.stlab.faultflow.dto.petrinet;

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
