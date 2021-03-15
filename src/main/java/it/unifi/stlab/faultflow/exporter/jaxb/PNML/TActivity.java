
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per tActivity complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{pnml.apromore.org}targets" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}sources" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="suppressJoinFailure" type="{pnml.apromore.org}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tActivity", namespace = "pnml.apromore.org", propOrder = {
    "targets",
    "sources"
})
@XmlSeeAlso({
    TWait.class,
    TInvoke.class,
    TEmpty.class,
    TReply.class,
    TReceive.class,
    TAssign.class
})
public class TActivity
    extends TExtensibleElements
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected TTargets targets;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TSources sources;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "suppressJoinFailure")
    protected TBoolean suppressJoinFailure;

    /**
     * Recupera il valore della proprietà targets.
     * 
     * @return
     *     possible object is
     *     {@link TTargets }
     *     
     */
    public TTargets getTargets() {
        return targets;
    }

    /**
     * Imposta il valore della proprietà targets.
     * 
     * @param value
     *     allowed object is
     *     {@link TTargets }
     *     
     */
    public void setTargets(TTargets value) {
        this.targets = value;
    }

    /**
     * Recupera il valore della proprietà sources.
     * 
     * @return
     *     possible object is
     *     {@link TSources }
     *     
     */
    public TSources getSources() {
        return sources;
    }

    /**
     * Imposta il valore della proprietà sources.
     * 
     * @param value
     *     allowed object is
     *     {@link TSources }
     *     
     */
    public void setSources(TSources value) {
        this.sources = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà suppressJoinFailure.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getSuppressJoinFailure() {
        return suppressJoinFailure;
    }

    /**
     * Imposta il valore della proprietà suppressJoinFailure.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setSuppressJoinFailure(TBoolean value) {
        this.suppressJoinFailure = value;
    }

}
