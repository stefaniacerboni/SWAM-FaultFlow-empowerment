
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * 
 *         XSD Authors: The child element correlations needs to be a Local Element Declaration,
 *         because there is another correlations element defined for the invoke activity.
 *       
 * 
 * <p>Classe Java per tReceive complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tReceive">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tActivity">
 *       &lt;sequence>
 *         &lt;element name="correlations" type="{pnml.apromore.org}tCorrelations" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}fromParts" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="partnerLink" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="portType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="operation" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="variable" type="{pnml.apromore.org}BPELVariableName" />
 *       &lt;attribute name="createInstance" type="{pnml.apromore.org}tBoolean" default="no" />
 *       &lt;attribute name="messageExchange" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tReceive", namespace = "pnml.apromore.org", propOrder = {
    "correlations",
    "fromParts"
})
public class TReceive
    extends TActivity
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected TCorrelations correlations;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TFromParts fromParts;
    @XmlAttribute(name = "partnerLink", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String partnerLink;
    @XmlAttribute(name = "portType")
    protected QName portType;
    @XmlAttribute(name = "operation", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String operation;
    @XmlAttribute(name = "variable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String variable;
    @XmlAttribute(name = "createInstance")
    protected TBoolean createInstance;
    @XmlAttribute(name = "messageExchange")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String messageExchange;

    /**
     * Recupera il valore della proprietà correlations.
     * 
     * @return
     *     possible object is
     *     {@link TCorrelations }
     *     
     */
    public TCorrelations getCorrelations() {
        return correlations;
    }

    /**
     * Imposta il valore della proprietà correlations.
     * 
     * @param value
     *     allowed object is
     *     {@link TCorrelations }
     *     
     */
    public void setCorrelations(TCorrelations value) {
        this.correlations = value;
    }

    /**
     * Recupera il valore della proprietà fromParts.
     * 
     * @return
     *     possible object is
     *     {@link TFromParts }
     *     
     */
    public TFromParts getFromParts() {
        return fromParts;
    }

    /**
     * Imposta il valore della proprietà fromParts.
     * 
     * @param value
     *     allowed object is
     *     {@link TFromParts }
     *     
     */
    public void setFromParts(TFromParts value) {
        this.fromParts = value;
    }

    /**
     * Recupera il valore della proprietà partnerLink.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerLink() {
        return partnerLink;
    }

    /**
     * Imposta il valore della proprietà partnerLink.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerLink(String value) {
        this.partnerLink = value;
    }

    /**
     * Recupera il valore della proprietà portType.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getPortType() {
        return portType;
    }

    /**
     * Imposta il valore della proprietà portType.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setPortType(QName value) {
        this.portType = value;
    }

    /**
     * Recupera il valore della proprietà operation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Imposta il valore della proprietà operation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Recupera il valore della proprietà variable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Imposta il valore della proprietà variable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariable(String value) {
        this.variable = value;
    }

    /**
     * Recupera il valore della proprietà createInstance.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getCreateInstance() {
        if (createInstance == null) {
            return TBoolean.NO;
        } else {
            return createInstance;
        }
    }

    /**
     * Imposta il valore della proprietà createInstance.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setCreateInstance(TBoolean value) {
        this.createInstance = value;
    }

    /**
     * Recupera il valore della proprietà messageExchange.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageExchange() {
        return messageExchange;
    }

    /**
     * Imposta il valore della proprietà messageExchange.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageExchange(String value) {
        this.messageExchange = value;
    }

}
