package it.unifi.stlab.DTO.faultTree;

public enum NodeType {

	BASIC_EVENT,
	GATE,
	FAILURE;

	public static NodeType fromString(String className ) {
		switch (className) {
		case "BasicEvent":
			return BASIC_EVENT;
		case "And":
			return GATE;
		case "Or":
			return GATE;
		case "Delay":
			return GATE;
		case "KOutOfN":
			return GATE;
		default:
			return null;
		}
	}
	
}
