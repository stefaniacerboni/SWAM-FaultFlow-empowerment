package it.unifi.stlab.exporter.jaxb;

import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java per anonymous complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tpn-entities" type="{http://www.oris-tool.org}TPN-entities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tpnEntities"
})
@XmlRootElement(name = "tpn-editor", namespace = "http://www.oris-tool.org")
public class TpnEditor {

    @XmlElement(name = "tpn-entities", namespace = "http://www.oris-tool.org", required = true)
    protected TPNEntities tpnEntities;

    /**
     * Recupera il valore della proprietà tpnEntities.
     *
     * @return possible object is
     * {@link TPNEntities }
     */
    public TPNEntities getTpnEntities() {
        return tpnEntities;
    }

    /**
     * Imposta il valore della proprietà tpnEntities.
     *
     * @param value allowed object is
     *              {@link TPNEntities }
     */
    public void setTpnEntities(TPNEntities value) {
        this.tpnEntities = value;
    }

}
