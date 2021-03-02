package it.unifi.stlab.faultflow.exporter;

public enum PetriNetExportMethod {
    FAULT_INJECTION,
    FAULT_ANALYSIS;

    public static PetriNetExportMethod fromString(String method ) {
        switch (method) {
            case "FI":
            case "fi":
            case "FaultInjection":
                return FAULT_INJECTION;
            default:
                return FAULT_ANALYSIS;
        }
    }
}

