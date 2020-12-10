package it.unifi.stlab.dto.system;

import java.util.List;

public class CompositionPortDto {
    private String parent;
    private List<String> children;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
