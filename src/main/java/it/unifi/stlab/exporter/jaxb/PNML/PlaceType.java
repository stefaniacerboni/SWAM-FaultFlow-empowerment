
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Place Types
 * 
 * <p>Classe Java per placeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="placeType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}nodeType">
 *       &lt;sequence>
 *         &lt;element name="initialMarking" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="toolspecific" type="{pnml.apromore.org}placeToolspecificType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeType", namespace = "pnml.apromore.org", propOrder = {
    "initialMarking",
    "toolspecific"
})
public class PlaceType
    extends NodeType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected PlaceType.InitialMarking initialMarking;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<PlaceToolspecificType> toolspecific;

    /**
     * Recupera il valore della proprietà initialMarking.
     * 
     * @return
     *     possible object is
     *     {@link PlaceType.InitialMarking }
     *     
     */
    public PlaceType.InitialMarking getInitialMarking() {
        return initialMarking;
    }

    /**
     * Imposta il valore della proprietà initialMarking.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceType.InitialMarking }
     *     
     */
    public void setInitialMarking(PlaceType.InitialMarking value) {
        this.initialMarking = value;
    }

    /**
     * Gets the value of the toolspecific property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toolspecific property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToolspecific().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlaceToolspecificType }
     * 
     * 
     */
    public List<PlaceToolspecificType> getToolspecific() {
        if (toolspecific == null) {
            toolspecific = new ArrayList<PlaceToolspecificType>();
        }
        return this.toolspecific;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "text"
    })
    public static class InitialMarking {

        @XmlElement(namespace = "pnml.apromore.org", required = true)
        protected String text;

        /**
         * Recupera il valore della proprietà text.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getText() {
            return text;
        }

        /**
         * Imposta il valore della proprietà text.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setText(String value) {
            this.text = value;
        }

    }

}
