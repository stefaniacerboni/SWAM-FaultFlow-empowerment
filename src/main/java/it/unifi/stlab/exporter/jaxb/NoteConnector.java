package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java per Note-connector complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="Note-connector">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" type="{http://www.oris-tool.org}empty"/>
 *         &lt;element name="properties" type="{http://www.oris-tool.org}empty"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.oris-tool.org}arc"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Note-connector", namespace = "http://www.oris-tool.org", propOrder = {
        "features",
        "properties"
})
public class NoteConnector {

    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected Empty features;
    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected Empty properties;
    @XmlAttribute(name = "from", required = true)
    protected String from;
    @XmlAttribute(name = "to", required = true)
    protected String to;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;

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
     * Recupera il valore della proprietà from.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFrom() {
        return from;
    }

    /**
     * Imposta il valore della proprietà from.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Recupera il valore della proprietà to.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTo() {
        return to;
    }

    /**
     * Imposta il valore della proprietà to.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTo(String value) {
        this.to = value;
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

}
