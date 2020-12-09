package it.unifi.stlab.DTO.PetriNet;

import java.util.List;

public class TransitionDto {
    private String name;
    private List<FeaturesDto> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FeaturesDto> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesDto> features) {
        this.features = features;
    }
}
