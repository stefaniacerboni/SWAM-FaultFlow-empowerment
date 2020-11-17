
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Classe Java per tPartnerLink complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tPartnerLink">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="partnerLinkType" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="myRole" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="partnerRole" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="initializePartnerRole" type="{pnml.apromore.org}tBoolean" />
 *       &lt;attribute name="WSDL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPartnerLink", namespace = "pnml.apromore.org")
public class TPartnerLink
    extends TExtensibleElements
{

    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "partnerLinkType", required = true)
    protected QName partnerLinkType;
    @XmlAttribute(name = "myRole")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String myRole;
    @XmlAttribute(name = "partnerRole")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String partnerRole;
    @XmlAttribute(name = "initializePartnerRole")
    protected TBoolean initializePartnerRole;
    @XmlAttribute(name = "WSDL")
    protected String wsdl;

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
     * Recupera il valore della proprietà partnerLinkType.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getPartnerLinkType() {
        return partnerLinkType;
    }

    /**
     * Imposta il valore della proprietà partnerLinkType.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setPartnerLinkType(QName value) {
        this.partnerLinkType = value;
    }

    /**
     * Recupera il valore della proprietà myRole.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMyRole() {
        return myRole;
    }

    /**
     * Imposta il valore della proprietà myRole.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMyRole(String value) {
        this.myRole = value;
    }

    /**
     * Recupera il valore della proprietà partnerRole.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerRole() {
        return partnerRole;
    }

    /**
     * Imposta il valore della proprietà partnerRole.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerRole(String value) {
        this.partnerRole = value;
    }

    /**
     * Recupera il valore della proprietà initializePartnerRole.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getInitializePartnerRole() {
        return initializePartnerRole;
    }

    /**
     * Imposta il valore della proprietà initializePartnerRole.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setInitializePartnerRole(TBoolean value) {
        this.initializePartnerRole = value;
    }

    /**
     * Recupera il valore della proprietà wsdl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWSDL() {
        return wsdl;
    }

    /**
     * Imposta il valore della proprietà wsdl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWSDL(String value) {
        this.wsdl = value;
    }

}
