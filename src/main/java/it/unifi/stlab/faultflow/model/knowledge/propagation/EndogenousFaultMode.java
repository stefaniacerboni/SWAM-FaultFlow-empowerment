package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "endogenousFaultModes")
public class EndogenousFaultMode extends FaultMode {
    @Transient
    private RealDistribution arisingPDF;
    @Column(name = "arisingPDF")
    private String arisingPDFString;

    public EndogenousFaultMode() {
        super.name = "";
        this.arisingPDF = null;
    }

    public EndogenousFaultMode(String name) {
        this();
        this.name = name;
    }

    public EndogenousFaultMode(String name, RealDistribution arisingPDF) {
        this(name);
        this.arisingPDF = arisingPDF;
        this.arisingPDFString = PDFParser.parseRealDistributionToString(arisingPDF);
    }

    public RealDistribution getArisingPDF() {
        return arisingPDF;
    }

    public void setArisingPDF(String arisingPDF) {
        this.arisingPDF = PDFParser.parseStringToRealDistribution(arisingPDF);
        this.arisingPDFString = arisingPDF;
    }

    public String getArisingPDFToString() {
        return PDFParser.parseRealDistributionToString(this.arisingPDF);
    }
}

