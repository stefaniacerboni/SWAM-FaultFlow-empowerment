
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tCopy complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tCopy">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{pnml.apromore.org}from"/>
 *         &lt;element ref="{pnml.apromore.org}to"/>
 *       &lt;/sequence>
 *       &lt;attribute name="keepSrcElementName" type="{pnml.apromore.org}tBoolean" default="no" />
 *       &lt;attribute name="ignoreMissingFromData" type="{pnml.apromore.org}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCopy", namespace = "pnml.apromore.org", propOrder = {
    "from",
    "to"
})
public class TCopy
    extends TExtensibleElements
{

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected TFrom from;
    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected TTo to;
    @XmlAttribute(name = "keepSrcElementName")
    protected TBoolean keepSrcElementName;
    @XmlAttribute(name = "ignoreMissingFromData")
    protected TBoolean ignoreMissingFromData;

    /**
     * Recupera il valore della proprietà from.
     * 
     * @return
     *     possible object is
     *     {@link TFrom }
     *     
     */
    public TFrom getFrom() {
        return from;
    }

    /**
     * Imposta il valore della proprietà from.
     * 
     * @param value
     *     allowed object is
     *     {@link TFrom }
     *     
     */
    public void setFrom(TFrom value) {
        this.from = value;
    }

    /**
     * Recupera il valore della proprietà to.
     * 
     * @return
     *     possible object is
     *     {@link TTo }
     *     
     */
    public TTo getTo() {
        return to;
    }

    /**
     * Imposta il valore della proprietà to.
     * 
     * @param value
     *     allowed object is
     *     {@link TTo }
     *     
     */
    public void setTo(TTo value) {
        this.to = value;
    }

    /**
     * Recupera il valore della proprietà keepSrcElementName.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getKeepSrcElementName() {
        if (keepSrcElementName == null) {
            return TBoolean.NO;
        } else {
            return keepSrcElementName;
        }
    }

    /**
     * Imposta il valore della proprietà keepSrcElementName.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setKeepSrcElementName(TBoolean value) {
        this.keepSrcElementName = value;
    }

    /**
     * Recupera il valore della proprietà ignoreMissingFromData.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getIgnoreMissingFromData() {
        if (ignoreMissingFromData == null) {
            return TBoolean.NO;
        } else {
            return ignoreMissingFromData;
        }
    }

    /**
     * Imposta il valore della proprietà ignoreMissingFromData.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setIgnoreMissingFromData(TBoolean value) {
        this.ignoreMissingFromData = value;
    }

}
