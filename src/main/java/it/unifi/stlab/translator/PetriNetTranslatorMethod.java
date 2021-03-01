package it.unifi.stlab.translator;

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
