package it.unifi.stlab.faultflow.dto.inputsystemdto.bdd;

import java.util.List;

public class InputBddDto {
    private List<InputBlockDto> blocks;
    private List<InputParentingDto> parentings;
    private String rootId;

    public List<InputBlockDto> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<InputBlockDto> blocks) {
        this.blocks = blocks;
    }

    public List<InputParentingDto> getParentings() {
        return parentings;
    }

    public void setParentings(List<InputParentingDto> parentings) {
        this.parentings = parentings;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
}
