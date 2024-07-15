package it.unifi.stlab.faultflow.dto.analysis;

import java.util.ArrayList;
import java.util.List;

public class TransientAnalysisDto {

    private final String concreteComponentSerialNumber;
    private final int time;
    private final double step;
    private final List<RewardDto> rewards;
    private Boolean hasFailed;

    public TransientAnalysisDto(String concreteComponentSerialNumber, int time, double step) {
        this.concreteComponentSerialNumber = concreteComponentSerialNumber;
        this.time = time;
        this.step = step;
        rewards = new ArrayList<>();
    }

    public void addReward(RewardDto rewardDTO) {
        rewards.add(rewardDTO);
    }

    public String getConcreteComponentSerialNumber() {
        return concreteComponentSerialNumber;
    }

    public int getTime() {
        return time;
    }

    public double getStep() {
        return step;
    }

    public List<RewardDto> getRewards() {
        return rewards;
    }

    public Boolean isHasFailed() {
        return hasFailed;
    }

    public void setHasFailed(boolean hasFailed) {
        this.hasFailed = hasFailed;
    }
}
