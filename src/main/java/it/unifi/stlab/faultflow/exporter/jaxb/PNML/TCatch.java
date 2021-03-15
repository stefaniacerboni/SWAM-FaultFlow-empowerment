
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Classe Java per tCatch complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tCatch">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tActivityContainer">
 *       &lt;attribute name="faultName" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="faultVariable" type="{pnml.apromore.org}BPELVariableName" />
 *       &lt;attribute name="faultMessageType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="faultElement" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCatch", namespace = "pnml.apromore.org")
public class TCatch
    extends TActivityContainer
{

    @XmlAttribute(name = "faultName")
    protected QName faultName;
    @XmlAttribute(name = "faultVariable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String faultVariable;
    @XmlAttribute(name = "faultMessageType")
    protected QName faultMessageType;
    @XmlAttribute(name = "faultElement")
    protected QName faultElement;

    /**
     * Recupera il valore della proprietà faultName.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getFaultName() {
        return faultName;
    }

    /**
     * Imposta il valore della proprietà faultName.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setFaultName(QName value) {
        this.faultName = value;
    }

    /**
     * Recupera il valore della proprietà faultVariable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultVariable() {
        return faultVariable;
    }

    /**
     * Imposta il valore della proprietà faultVariable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultVariable(String value) {
        this.faultVariable = value;
    }

    /**
     * Recupera il valore della proprietà faultMessageType.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getFaultMessageType() {
        return faultMessageType;
    }

    /**
     * Imposta il valore della proprietà faultMessageType.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setFaultMessageType(QName value) {
        this.faultMessageType = value;
    }

    /**
     * Recupera il valore della proprietà faultElement.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getFaultElement() {
        return faultElement;
    }

    /**
     * Imposta il valore della proprietà faultElement.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setFaultElement(QName value) {
        this.faultElement = value;
    }

}
