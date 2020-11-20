package it.unifi.stlab.DTO.faultTree;

import java.util.List;

public class InputFaultTreeDto {

	private List<InputNodeDto> nodes;
	private List<InputParentingDto> parentings;
	private String rootId;
	
	public List<InputNodeDto> getNodes() {
		return nodes;
	}
	public void setNodes(List<InputNodeDto> nodes) {
		this.nodes = nodes;
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
