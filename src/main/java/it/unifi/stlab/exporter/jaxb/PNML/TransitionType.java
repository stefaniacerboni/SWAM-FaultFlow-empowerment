
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Transition Types
 * 
 * <p>Classe Java per transitionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="transitionType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}nodeType">
 *       &lt;sequence>
 *         &lt;element name="toolspecific" type="{pnml.apromore.org}transitionToolspecificType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transitionType", namespace = "pnml.apromore.org", propOrder = {
    "toolspecific"
})
public class TransitionType
    extends NodeType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected List<TransitionToolspecificType> toolspecific;

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
     * {@link TransitionToolspecificType }
     * 
     * 
     */
    public List<TransitionToolspecificType> getToolspecific() {
        if (toolspecific == null) {
            toolspecific = new ArrayList<TransitionToolspecificType>();
        }
        return this.toolspecific;
    }

}
