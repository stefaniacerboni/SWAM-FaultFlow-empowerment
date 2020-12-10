package it.unifi.stlab.dto.faulttree;

public enum GateType {

	AND,
	OR,
	DELAY,
	KOUTOFN;
	
	public static GateType fromString( String className ) {
		switch (className) {
		case "And":
			return AND;
		case "Or":
			return OR;
		case "Delay":
			return DELAY;
		case "KOutOfN":
			return KOUTOFN;
		default:
			return null;
		}
	}
	
}
