package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ListResourceProperty complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType name="ListResourceProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="property" type="{http://www.oris-tool.org}ResourceProperty"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListResourceProperty", namespace = "http://www.oris-tool.org", propOrder = {
        "property"
})
public class ListResourceProperty {

    @XmlElement(namespace = "http://www.oris-tool.org", required = true)
    protected ResourceProperty property;

    /**
     * Recupera il valore della proprietà property.
     *
     * @return possible object is
     * {@link ResourceProperty }
     */
    public ResourceProperty getProperty() {
        return property;
    }

    /**
     * Imposta il valore della proprietà property.
     *
     * @param value allowed object is
     *              {@link ResourceProperty }
     */
    public void setProperty(ResourceProperty value) {
        this.property = value;
    }

}
