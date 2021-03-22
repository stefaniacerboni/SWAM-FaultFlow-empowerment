package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "endogenousFaultModes")
public class EndogenousFaultMode extends FaultMode {
    @Column(name = "arising_PDF")
    private String arisingPDF;

    public EndogenousFaultMode() {
        super.name = "";
        this.arisingPDF = null;
    }

    public EndogenousFaultMode(String name) {
        this();
        this.name = name;
    }

    public EndogenousFaultMode(String name, String arisingPDF) {
        this(name);
        this.arisingPDF = arisingPDF;
    }

    public RealDistribution getArisingPDF() {
        return PDFParser.parseStringToRealDistribution(arisingPDF);
    }


    public void setArisingPDF(String arisingPDF) {
        this.arisingPDF = arisingPDF;
    }

    public String getArisingPDFToString() {
        return this.arisingPDF;
    }
}

