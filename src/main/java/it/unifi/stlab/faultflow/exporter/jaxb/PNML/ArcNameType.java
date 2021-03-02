
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Extension see "http://www.informatik.hu-berlin.de/top/pnml/conv.rng"
 * 
 * <p>Classe Java per arcNameType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="arcNameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="graphics" type="{pnml.apromore.org}annotationGraphisType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arcNameType", namespace = "pnml.apromore.org", propOrder = {
    "text",
    "graphics"
})
public class ArcNameType {

    @XmlElement(namespace = "pnml.apromore.org")
    protected int text;
    @XmlElement(namespace = "pnml.apromore.org")
    protected AnnotationGraphisType graphics;

    /**
     * Recupera il valore della proprietà text.
     * 
     */
    public int getText() {
        return text;
    }

    /**
     * Imposta il valore della proprietà text.
     * 
     */
    public void setText(int value) {
        this.text = value;
    }

    /**
     * Recupera il valore della proprietà graphics.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationGraphisType }
     *     
     */
    public AnnotationGraphisType getGraphics() {
        return graphics;
    }

    /**
     * Imposta il valore della proprietà graphics.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationGraphisType }
     *     
     */
    public void setGraphics(AnnotationGraphisType value) {
        this.graphics = value;
    }

}
