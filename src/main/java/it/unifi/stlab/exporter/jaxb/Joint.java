package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java per Joint complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="Joint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" type="{http://www.oris-tool.org}empty"/>
 *         &lt;element name="properties" type="{http://www.oris-tool.org}empty"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.oris-tool.org}node"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Joint", namespace = "http://www.oris-tool.org", propOrder = {
        "features",
        "properties"
})
public class Joint {

    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected Empty features;
    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected Empty properties;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "y", required = true)
    protected int y;
    @XmlAttribute(name = "x", required = true)
    protected int x;

    /**
     * Recupera il valore della proprietà features.
     *
     * @return possible object is
     * {@link Empty }
     */
    public Empty getFeatures() {
        return features;
    }

    /**
     * Imposta il valore della proprietà features.
     *
     * @param value allowed object is
     *              {@link Empty }
     */
    public void setFeatures(Empty value) {
        this.features = value;
    }

    /**
     * Recupera il valore della proprietà properties.
     *
     * @return possible object is
     * {@link Empty }
     */
    public Empty getProperties() {
        return properties;
    }

    /**
     * Imposta il valore della proprietà properties.
     *
     * @param value allowed object is
     *              {@link Empty }
     */
    public void setProperties(Empty value) {
        this.properties = value;
    }

    /**
     * Recupera il valore della proprietà uuid.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Imposta il valore della proprietà uuid.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Recupera il valore della proprietà y.
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della proprietà y.
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Recupera il valore della proprietà x.
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della proprietà x.
     */
    public void setX(int value) {
        this.x = value;
    }

}
