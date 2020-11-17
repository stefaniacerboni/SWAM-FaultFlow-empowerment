
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         XSD Authors: The child element correlation needs to be a Local Element Declaration,
 *         because there is another correlation element defined for the non-invoke activities.
 *       
 * 
 * <p>Classe Java per tCorrelationsWithPattern complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tCorrelationsWithPattern">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element name="correlation" type="{pnml.apromore.org}tCorrelationWithPattern" maxOccurs="unbounded"/>
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
@XmlType(name = "tCorrelationsWithPattern", namespace = "pnml.apromore.org", propOrder = {
    "correlation"
})
public class TCorrelationsWithPattern
    extends TExtensibleElements
{

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected List<TCorrelationWithPattern> correlation;

    /**
     * Gets the value of the correlation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the correlation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorrelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCorrelationWithPattern }
     * 
     * 
     */
    public List<TCorrelationWithPattern> getCorrelation() {
        if (correlation == null) {
            correlation = new ArrayList<TCorrelationWithPattern>();
        }
        return this.correlation;
    }

}
