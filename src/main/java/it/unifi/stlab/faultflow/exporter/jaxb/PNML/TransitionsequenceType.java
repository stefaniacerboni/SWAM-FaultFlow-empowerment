
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per transitionsequenceType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="transitionsequenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="occuredtransition" type="{pnml.apromore.org}occuredtransitionType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transitionsequenceType", namespace = "pnml.apromore.org", propOrder = {
    "occuredtransition"
})
public class TransitionsequenceType {

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected List<OccuredtransitionType> occuredtransition;

    /**
     * Gets the value of the occuredtransition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occuredtransition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccuredtransition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OccuredtransitionType }
     * 
     * 
     */
    public List<OccuredtransitionType> getOccuredtransition() {
        if (occuredtransition == null) {
            occuredtransition = new ArrayList<OccuredtransitionType>();
        }
        return this.occuredtransition;
    }

}
