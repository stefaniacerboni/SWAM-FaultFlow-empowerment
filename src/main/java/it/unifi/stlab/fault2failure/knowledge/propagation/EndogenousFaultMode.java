package it.unifi.stlab.fault2failure.knowledge.propagation;

import it.unifi.stlab.fault2failure.knowledge.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;
import org.oristool.models.stpn.trees.StochasticTransitionFeature;

public class EndogenousFaultMode extends FaultMode {
    private RealDistribution arisingPDF;

    public EndogenousFaultMode(String name) {
        this.name = name;
        this.arisingPDF = null;
    }

    public RealDistribution getArisingPDF() {
        return arisingPDF;
    }

    public String getArisingPDFToString(){
        return PDFParser.parseRealDistributionToString(this.arisingPDF);
    }

    public void setArisingPDF(String arisingPDF) {
        this.arisingPDF = PDFParser.parseStringToRealDistribution(arisingPDF);
    }
}

