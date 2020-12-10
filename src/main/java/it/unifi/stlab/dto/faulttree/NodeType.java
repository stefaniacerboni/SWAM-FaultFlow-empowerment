package it.unifi.stlab.dto.faulttree;

public enum NodeType {

	BASIC_EVENT,
	FAILURE,
	GATE;
	
	public static NodeType fromString( String className ) {
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
