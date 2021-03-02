
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per transitionResourceType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="transitionResourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="graphics" type="{pnml.apromore.org}graphicsSimpleType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="organizationalUnitName" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="roleName" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transitionResourceType", namespace = "pnml.apromore.org", propOrder = {
    "graphics"
})
@XmlSeeAlso({
    TransitionToolspecificType.TransitionResource.class
})
public class TransitionResourceType {

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected GraphicsSimpleType graphics;
    @XmlAttribute(name = "organizationalUnitName")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object organizationalUnitName;
    @XmlAttribute(name = "roleName")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object roleName;

    /**
     * Recupera il valore della proprietà graphics.
     * 
     * @return
     *     possible object is
     *     {@link GraphicsSimpleType }
     *     
     */
    public GraphicsSimpleType getGraphics() {
        return graphics;
    }

    /**
     * Imposta il valore della proprietà graphics.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphicsSimpleType }
     *     
     */
    public void setGraphics(GraphicsSimpleType value) {
        this.graphics = value;
    }

    /**
     * Recupera il valore della proprietà organizationalUnitName.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    /**
     * Imposta il valore della proprietà organizationalUnitName.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOrganizationalUnitName(Object value) {
        this.organizationalUnitName = value;
    }

    /**
     * Recupera il valore della proprietà roleName.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getRoleName() {
        return roleName;
    }

    /**
     * Imposta il valore della proprietà roleName.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setRoleName(Object value) {
        this.roleName = value;
    }

}
