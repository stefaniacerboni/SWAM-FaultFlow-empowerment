package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.utils.ParseString;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

public class EndogenousFaultMode extends FaultMode {
    private StochasticTransitionFeature arisingPDF;

    public EndogenousFaultMode(String name) {
        this.name = name;
        this.arisingPDF = null;
    }

    public StochasticTransitionFeature getArisingPDF() {
        return arisingPDF;
    }

    //TODO: move Parse function outside class
    public void setArisingPDF(String arisingPDF) {
        this.arisingPDF = ParseString.parseStringToPDF(arisingPDF);
    }
}

