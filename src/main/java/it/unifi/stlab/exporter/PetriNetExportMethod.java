package it.unifi.stlab.exporter;

import it.unifi.stlab.dto.inputsystemdto.faulttree.NodeType;

public enum PetriNetExportMethod {
    FAULT_INJECTION,
    FAULT_ANALYSIS;

    public static PetriNetExportMethod fromString(String method ) {
        switch (method) {
            case "FI":
            case "FaultInjection":
                return FAULT_INJECTION;
            default:
                return FAULT_ANALYSIS;
        }
    }
}

