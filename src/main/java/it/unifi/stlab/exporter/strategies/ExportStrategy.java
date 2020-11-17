package it.unifi.stlab.exporter.strategies;

import it.unifi.stlab.exporter.jaxb.TpnEditor;

public interface ExportStrategy {
    Object translate();
}
