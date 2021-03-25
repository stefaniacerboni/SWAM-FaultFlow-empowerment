package it.unifi.stlab.faultflow.dto.inputsystemdto.faulttree;

public enum GateType {

	AND,
	OR,
	DELAY,
	KOUTOFN;

	public static GateType fromString(String className) {
		switch (className) {
			case "AND":
			case "And":
				return AND;
			case "OR":
			case "Or":
				return OR;
			case "Delay":
				return DELAY;
			case "KOutOfN":
			case "KofN":
				return KOUTOFN;
			default:
				return null;
		}
	}
}
