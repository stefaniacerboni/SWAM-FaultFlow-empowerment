package it.unifi.stlab.faultflow.translator;

public enum PetriNetTranslatorMethod {
    CONCURRENT,
    DETERMINISTIC;

    public static PetriNetTranslatorMethod fromString(String method ) {
        switch (method) {
            case "Deterministic":
            case "D":
                return DETERMINISTIC;
            default:
                return CONCURRENT;
        }
    }
}
