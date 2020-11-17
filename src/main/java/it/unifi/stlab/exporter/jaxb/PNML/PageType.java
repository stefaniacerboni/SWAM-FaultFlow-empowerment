
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * structured &amp; modular it.unifi.stlab.exporter.jaxb.PNML Types
 * 
 * <p>Classe Java per pageType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pageType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}netType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="net" type="{pnml.apromore.org}netType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageType", namespace = "pnml.apromore.org", propOrder = {
    "net"
})
@XmlSeeAlso({
    NetType.Page.class
})
public class PageType
    extends NetType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected List<NetType> net;

    /**
     * Gets the value of the net property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the net property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NetType }
     * 
     * 
     */
    public List<NetType> getNet() {
        if (net == null) {
            net = new ArrayList<NetType>();
        }
        return this.net;
    }

}
