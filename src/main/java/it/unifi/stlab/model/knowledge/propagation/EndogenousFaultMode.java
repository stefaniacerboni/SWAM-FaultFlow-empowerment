package it.unifi.stlab.model.knowledge.propagation;

import it.unifi.stlab.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;

public class EndogenousFaultMode extends FaultMode {
    private RealDistribution arisingPDF;

    public EndogenousFaultMode(String name) {
        this.name = name;
        this.arisingPDF = null;
    }

    public EndogenousFaultMode(String name, RealDistribution arisingPDF) {
        this(name);
        this.arisingPDF = arisingPDF;
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

