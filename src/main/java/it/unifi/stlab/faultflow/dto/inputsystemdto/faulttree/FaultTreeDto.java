package it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree;

import java.util.List;

public class FaultTreeDto {

	private List<NodeDto> nodes;
	private List<ParentingDto> parentings;
	private List<String> topEvents;


	public List<NodeDto> getNodes() {
		return nodes;
	}
	public void setNodes(List<NodeDto> nodes) {
		this.nodes = nodes;
	}
	public List<ParentingDto> getParentings() {
		return parentings;
	}
	public void setParentings(List<ParentingDto> parentings) {
		this.parentings = parentings;
	}

	public List<String> getTopEvents() {
		return topEvents;
	}

	public void setTopEvents(List<String> topEvents) {
		this.topEvents = topEvents;
	}
}
