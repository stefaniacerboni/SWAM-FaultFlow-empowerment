
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per arcToolspecificType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="arcToolspecificType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}toolspecificType">
 *       &lt;sequence>
 *         &lt;element name="route" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="probability" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="displayProbabilityOn" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="displayProbabilityPosition" type="{pnml.apromore.org}positionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arcToolspecificType", namespace = "pnml.apromore.org", propOrder = {
    "route",
    "probability",
    "displayProbabilityOn",
    "displayProbabilityPosition"
})
public class ArcToolspecificType
    extends ToolspecificType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected Boolean route;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Double probability;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Boolean displayProbabilityOn;
    @XmlElement(namespace = "pnml.apromore.org")
    protected PositionType displayProbabilityPosition;

    /**
     * Recupera il valore della proprietà route.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRoute() {
        return route;
    }

    /**
     * Imposta il valore della proprietà route.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRoute(Boolean value) {
        this.route = value;
    }

    /**
     * Recupera il valore della proprietà probability.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * Imposta il valore della proprietà probability.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProbability(Double value) {
        this.probability = value;
    }

    /**
     * Recupera il valore della proprietà displayProbabilityOn.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisplayProbabilityOn() {
        return displayProbabilityOn;
    }

    /**
     * Imposta il valore della proprietà displayProbabilityOn.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisplayProbabilityOn(Boolean value) {
        this.displayProbabilityOn = value;
    }

    /**
     * Recupera il valore della proprietà displayProbabilityPosition.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getDisplayProbabilityPosition() {
        return displayProbabilityPosition;
    }

    /**
     * Imposta il valore della proprietà displayProbabilityPosition.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setDisplayProbabilityPosition(PositionType value) {
        this.displayProbabilityPosition = value;
    }

}
