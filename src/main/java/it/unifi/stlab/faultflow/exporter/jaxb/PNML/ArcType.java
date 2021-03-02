
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Arc Types
 * 
 * <p>Classe Java per arcType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="arcType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inscription" type="{pnml.apromore.org}arcNameType" minOccurs="0"/>
 *         &lt;element name="type" type="{pnml.apromore.org}arcTypeType" minOccurs="0"/>
 *         &lt;element name="graphics" type="{pnml.apromore.org}graphicsArcType" minOccurs="0"/>
 *         &lt;element name="toolspecific" type="{pnml.apromore.org}arcToolspecificType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="target" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arcType", namespace = "pnml.apromore.org", propOrder = {
    "inscription",
    "type",
    "graphics",
    "toolspecific"
})
public class ArcType {

    @XmlElement(namespace = "pnml.apromore.org")
    protected ArcNameType inscription;
    @XmlElement(namespace = "pnml.apromore.org")
    protected ArcTypeType type;
    @XmlElement(namespace = "pnml.apromore.org")
    protected GraphicsArcType graphics;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<ArcToolspecificType> toolspecific;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "source", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object source;
    @XmlAttribute(name = "target", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object target;

    /**
     * Recupera il valore della proprietà inscription.
     * 
     * @return
     *     possible object is
     *     {@link ArcNameType }
     *     
     */
    public ArcNameType getInscription() {
        return inscription;
    }

    /**
     * Imposta il valore della proprietà inscription.
     * 
     * @param value
     *     allowed object is
     *     {@link ArcNameType }
     *     
     */
    public void setInscription(ArcNameType value) {
        this.inscription = value;
    }

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link ArcTypeType }
     *     
     */
    public ArcTypeType getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link ArcTypeType }
     *     
     */
    public void setType(ArcTypeType value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprietà graphics.
     * 
     * @return
     *     possible object is
     *     {@link GraphicsArcType }
     *     
     */
    public GraphicsArcType getGraphics() {
        return graphics;
    }

    /**
     * Imposta il valore della proprietà graphics.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphicsArcType }
     *     
     */
    public void setGraphics(GraphicsArcType value) {
        this.graphics = value;
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
     * {@link ArcToolspecificType }
     * 
     * 
     */
    public List<ArcToolspecificType> getToolspecific() {
        if (toolspecific == null) {
            toolspecific = new ArrayList<ArcToolspecificType>();
        }
        return this.toolspecific;
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

    /**
     * Recupera il valore della proprietà source.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSource() {
        return source;
    }

    /**
     * Imposta il valore della proprietà source.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSource(Object value) {
        this.source = value;
    }

    /**
     * Recupera il valore della proprietà target.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Imposta il valore della proprietà target.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTarget(Object value) {
        this.target = value;
    }

}
