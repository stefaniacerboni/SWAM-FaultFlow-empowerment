
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per resourceMappingType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="resourceMappingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="resourceID" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="resourceClass" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceMappingType", namespace = "pnml.apromore.org")
public class ResourceMappingType {

    @XmlAttribute(name = "resourceID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object resourceID;
    @XmlAttribute(name = "resourceClass")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object resourceClass;

    /**
     * Recupera il valore della proprietà resourceID.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getResourceID() {
        return resourceID;
    }

    /**
     * Imposta il valore della proprietà resourceID.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setResourceID(Object value) {
        this.resourceID = value;
    }

    /**
     * Recupera il valore della proprietà resourceClass.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getResourceClass() {
        return resourceClass;
    }

    /**
     * Imposta il valore della proprietà resourceClass.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setResourceClass(Object value) {
        this.resourceClass = value;
    }

}
