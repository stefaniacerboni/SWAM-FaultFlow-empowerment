package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ListTransitionFeatures complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="ListTransitionFeatures">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="feature" type="{http://www.oris-tool.org}TransitionFeature" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListTransitionFeatures", namespace = "http://www.oris-tool.org", propOrder = {
        "feature"
})
public class ListTransitionFeatures {

    @XmlElement(namespace = "http://www.oris-tool.org")
    protected TransitionFeature feature;

    /**
     * Recupera il valore della proprietà feature.
     *
     * @return possible object is
     * {@link TransitionFeature }
     */
    public TransitionFeature getFeature() {
        return feature;
    }

    /**
     * Imposta il valore della proprietà feature.
     *
     * @param value allowed object is
     *              {@link TransitionFeature }
     */
    public void setFeature(TransitionFeature value) {
        this.feature = value;
    }

}
