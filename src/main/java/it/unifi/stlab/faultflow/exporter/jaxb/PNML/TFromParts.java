
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tFromParts complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tFromParts">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{pnml.apromore.org}fromPart" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFromParts", namespace = "pnml.apromore.org", propOrder = {
    "fromPart"
})
public class TFromParts
    extends TExtensibleElements
{

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected List<TFromPart> fromPart;

    /**
     * Gets the value of the fromPart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fromPart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFromPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TFromPart }
     * 
     * 
     */
    public List<TFromPart> getFromPart() {
        if (fromPart == null) {
            fromPart = new ArrayList<TFromPart>();
        }
        return this.fromPart;
    }

}
