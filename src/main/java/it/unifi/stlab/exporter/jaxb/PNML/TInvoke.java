
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
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
 *         because there is another correlations element defined for the non-invoke activities.
 *       
 * 
 * <p>Classe Java per tInvoke complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tInvoke">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tActivity">
 *       &lt;sequence>
 *         &lt;element name="correlations" type="{pnml.apromore.org}tCorrelationsWithPattern" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}catch" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}catchAll" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}compensationHandler" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}toParts" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}fromParts" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="partnerLink" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="portType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="operation" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="inputVariable" type="{pnml.apromore.org}BPELVariableName" />
 *       &lt;attribute name="outputVariable" type="{pnml.apromore.org}BPELVariableName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInvoke", namespace = "pnml.apromore.org", propOrder = {
    "correlations",
    "_catch",
    "catchAll",
    "compensationHandler",
    "toParts",
    "fromParts"
})
public class TInvoke
    extends TActivity
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected TCorrelationsWithPattern correlations;
    @XmlElement(name = "catch", namespace = "pnml.apromore.org")
    protected List<TCatch> _catch;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TActivityContainer catchAll;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TActivityContainer compensationHandler;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TToParts toParts;
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
    @XmlAttribute(name = "inputVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String inputVariable;
    @XmlAttribute(name = "outputVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputVariable;

    /**
     * Recupera il valore della proprietà correlations.
     * 
     * @return
     *     possible object is
     *     {@link TCorrelationsWithPattern }
     *     
     */
    public TCorrelationsWithPattern getCorrelations() {
        return correlations;
    }

    /**
     * Imposta il valore della proprietà correlations.
     * 
     * @param value
     *     allowed object is
     *     {@link TCorrelationsWithPattern }
     *     
     */
    public void setCorrelations(TCorrelationsWithPattern value) {
        this.correlations = value;
    }

    /**
     * Gets the value of the catch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the catch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCatch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCatch }
     * 
     * 
     */
    public List<TCatch> getCatch() {
        if (_catch == null) {
            _catch = new ArrayList<TCatch>();
        }
        return this._catch;
    }

    /**
     * Recupera il valore della proprietà catchAll.
     * 
     * @return
     *     possible object is
     *     {@link TActivityContainer }
     *     
     */
    public TActivityContainer getCatchAll() {
        return catchAll;
    }

    /**
     * Imposta il valore della proprietà catchAll.
     * 
     * @param value
     *     allowed object is
     *     {@link TActivityContainer }
     *     
     */
    public void setCatchAll(TActivityContainer value) {
        this.catchAll = value;
    }

    /**
     * Recupera il valore della proprietà compensationHandler.
     * 
     * @return
     *     possible object is
     *     {@link TActivityContainer }
     *     
     */
    public TActivityContainer getCompensationHandler() {
        return compensationHandler;
    }

    /**
     * Imposta il valore della proprietà compensationHandler.
     * 
     * @param value
     *     allowed object is
     *     {@link TActivityContainer }
     *     
     */
    public void setCompensationHandler(TActivityContainer value) {
        this.compensationHandler = value;
    }

    /**
     * Recupera il valore della proprietà toParts.
     * 
     * @return
     *     possible object is
     *     {@link TToParts }
     *     
     */
    public TToParts getToParts() {
        return toParts;
    }

    /**
     * Imposta il valore della proprietà toParts.
     * 
     * @param value
     *     allowed object is
     *     {@link TToParts }
     *     
     */
    public void setToParts(TToParts value) {
        this.toParts = value;
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
     * Recupera il valore della proprietà inputVariable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputVariable() {
        return inputVariable;
    }

    /**
     * Imposta il valore della proprietà inputVariable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputVariable(String value) {
        this.inputVariable = value;
    }

    /**
     * Recupera il valore della proprietà outputVariable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputVariable() {
        return outputVariable;
    }

    /**
     * Imposta il valore della proprietà outputVariable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputVariable(String value) {
        this.outputVariable = value;
    }

}
