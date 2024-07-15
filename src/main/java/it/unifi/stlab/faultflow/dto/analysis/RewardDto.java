package it.unifi.stlab.faultflow.dto.analysis;

import java.util.ArrayList;
import java.util.List;

public class RewardDto {

    private String rewardName;
    private List<Double> values;

    public RewardDto(String rewardName) {
        this.rewardName = rewardName;
        values = new ArrayList<>();
    }

    public void addValue(double value) {
        values.add(value);
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}

