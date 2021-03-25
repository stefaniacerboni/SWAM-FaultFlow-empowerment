package it.unifi.stlab.faultflow.model.knowledge.propagation;

import it.unifi.stlab.faultflow.model.utils.PDFParser;
import org.apache.commons.math3.distribution.RealDistribution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EndogenousFaultMode that = (EndogenousFaultMode) o;
        return Objects.equals(arisingPDF, that.arisingPDF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), arisingPDF);
    }
}

