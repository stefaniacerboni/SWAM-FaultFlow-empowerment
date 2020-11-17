
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tTargets complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tTargets">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{pnml.apromore.org}joinCondition" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}target" maxOccurs="unbounded"/>
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
@XmlType(name = "tTargets", namespace = "pnml.apromore.org", propOrder = {
    "joinCondition",
    "target"
})
public class TTargets
    extends TExtensibleElements
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected TCondition joinCondition;
    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected List<TTarget> target;

    /**
     * Recupera il valore della proprietà joinCondition.
     * 
     * @return
     *     possible object is
     *     {@link TCondition }
     *     
     */
    public TCondition getJoinCondition() {
        return joinCondition;
    }

    /**
     * Imposta il valore della proprietà joinCondition.
     * 
     * @param value
     *     allowed object is
     *     {@link TCondition }
     *     
     */
    public void setJoinCondition(TCondition value) {
        this.joinCondition = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the target property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TTarget }
     * 
     * 
     */
    public List<TTarget> getTarget() {
        if (target == null) {
            target = new ArrayList<TTarget>();
        }
        return this.target;
    }

}