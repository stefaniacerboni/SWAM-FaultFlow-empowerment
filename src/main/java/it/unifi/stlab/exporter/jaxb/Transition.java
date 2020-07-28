
package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Transition complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Transition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" type="{http://www.oris-tool.org}ListTransitionFeatures"/>
 *         &lt;element name="properties" type="{http://www.oris-tool.org}ListTransitionProperty"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.oris-tool.org}node"/>
 *       &lt;attribute name="rotation-angle" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transition", namespace = "http://www.oris-tool.org", propOrder = {
    "features",
    "properties"
})
public class Transition {

    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected ListTransitionFeatures features;
    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected ListTransitionProperty properties;
    @XmlAttribute(name = "rotation-angle", required = true)
    protected float rotationAngle;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "y", required = true)
    protected int y;
    @XmlAttribute(name = "x", required = true)
    protected int x;

    /**
     * Recupera il valore della proprietà features.
     * 
     * @return
     *     possible object is
     *     {@link ListTransitionFeatures }
     *     
     */
    public ListTransitionFeatures getFeatures() {
        return features;
    }

    /**
     * Imposta il valore della proprietà features.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTransitionFeatures }
     *     
     */
    public void setFeatures(ListTransitionFeatures value) {
        this.features = value;
    }

    /**
     * Recupera il valore della proprietà properties.
     * 
     * @return
     *     possible object is
     *     {@link ListTransitionProperty }
     *     
     */
    public ListTransitionProperty getProperties() {
        return properties;
    }

    /**
     * Imposta il valore della proprietà properties.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTransitionProperty }
     *     
     */
    public void setProperties(ListTransitionProperty value) {
        this.properties = value;
    }

    /**
     * Recupera il valore della proprietà rotationAngle.
     * 
     */
    public float getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Imposta il valore della proprietà rotationAngle.
     * 
     */
    public void setRotationAngle(float value) {
        this.rotationAngle = value;
    }

    /**
     * Recupera il valore della proprietà uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Imposta il valore della proprietà uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Recupera il valore della proprietà y.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della proprietà y.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Recupera il valore della proprietà x.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della proprietà x.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

}
