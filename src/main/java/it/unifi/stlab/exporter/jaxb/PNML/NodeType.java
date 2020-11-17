
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * General Node Types
 * 
 * <p>Classe Java per nodeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="nodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{pnml.apromore.org}nodeNameType" minOccurs="0"/>
 *         &lt;element name="graphics" type="{pnml.apromore.org}graphicsNodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodeType", namespace = "pnml.apromore.org", propOrder = {
    "name",
    "graphics"
})
@XmlSeeAlso({
    PlaceType.class,
    TransitionType.class
})
public class NodeType {

    @XmlElement(namespace = "pnml.apromore.org")
    protected NodeNameType name;
    @XmlElement(namespace = "pnml.apromore.org")
    protected GraphicsNodeType graphics;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link NodeNameType }
     *     
     */
    public NodeNameType getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeNameType }
     *     
     */
    public void setName(NodeNameType value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà graphics.
     * 
     * @return
     *     possible object is
     *     {@link GraphicsNodeType }
     *     
     */
    public GraphicsNodeType getGraphics() {
        return graphics;
    }

    /**
     * Imposta il valore della proprietà graphics.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphicsNodeType }
     *     
     */
    public void setGraphics(GraphicsNodeType value) {
        this.graphics = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
