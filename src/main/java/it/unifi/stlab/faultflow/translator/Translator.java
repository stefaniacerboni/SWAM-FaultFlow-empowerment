package it.unifi.stlab.faultflow.translator;

import it.unifi.stlab.faultflow.model.knowledge.composition.System;

public interface Translator {
    void translate(System system);

}
