package it.unifi.stlab.faultflow.dto.petrinet;

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
