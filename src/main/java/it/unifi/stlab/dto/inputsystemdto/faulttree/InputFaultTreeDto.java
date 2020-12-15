package it.unifi.stlab.dto.inputsystemdto.faulttree;

import java.util.List;

public class InputFaultTreeDto {

	private List<InputNodeDto> nodes;
	private List<InputParentingDto> parentings;
	private List<String> topEvents;


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

	public List<String> getTopEvents() {
		return topEvents;
	}

	public void setTopEvents(List<String> topEvents) {
		this.topEvents = topEvents;
	}
}
