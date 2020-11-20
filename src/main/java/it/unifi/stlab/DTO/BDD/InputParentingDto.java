package it.unifi.stlab.DTO.BDD;

public class InputParentingDto {
    private String parentId;
    private String childId;
    private String label;

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getChildId() {
        return childId;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
