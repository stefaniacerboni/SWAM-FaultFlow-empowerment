package it.unifi.stlab.faultflow.exporter.jaxb;

import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java per Resource complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="Resource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" type="{http://www.oris-tool.org}empty"/>
 *         &lt;element name="properties" type="{http://www.oris-tool.org}ListResourceProperty"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resource", namespace = "http://www.oris-tool.org", propOrder = {
        "features",
        "properties"
})
public class Resource {

    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected Empty features;
    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected ListResourceProperty properties;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

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
     * {@link ListResourceProperty }
     */
    public ListResourceProperty getProperties() {
        return properties;
    }

    /**
     * Imposta il valore della proprietà properties.
     *
     * @param value allowed object is
     *              {@link ListResourceProperty }
     */
    public void setProperties(ListResourceProperty value) {
        this.properties = value;
    }

    /**
     * Recupera il valore della proprietà id.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

}
