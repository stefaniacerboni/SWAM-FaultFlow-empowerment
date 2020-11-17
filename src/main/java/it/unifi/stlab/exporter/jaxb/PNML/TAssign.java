
package it.unifi.stlab.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tAssign complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tAssign">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tActivity">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{pnml.apromore.org}copy"/>
 *           &lt;element ref="{pnml.apromore.org}extensionAssignOperation"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="validate" type="{pnml.apromore.org}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAssign", namespace = "pnml.apromore.org", propOrder = {
    "copyOrExtensionAssignOperation"
})
public class TAssign
    extends TActivity
{

    @XmlElements({
        @XmlElement(name = "copy", namespace = "pnml.apromore.org", type = TCopy.class),
        @XmlElement(name = "extensionAssignOperation", namespace = "pnml.apromore.org", type = TExtensionAssignOperation.class)
    })
    protected List<TExtensibleElements> copyOrExtensionAssignOperation;
    @XmlAttribute(name = "validate")
    protected TBoolean validate;

    /**
     * Gets the value of the copyOrExtensionAssignOperation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the copyOrExtensionAssignOperation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCopyOrExtensionAssignOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCopy }
     * {@link TExtensionAssignOperation }
     * 
     * 
     */
    public List<TExtensibleElements> getCopyOrExtensionAssignOperation() {
        if (copyOrExtensionAssignOperation == null) {
            copyOrExtensionAssignOperation = new ArrayList<TExtensibleElements>();
        }
        return this.copyOrExtensionAssignOperation;
    }

    /**
     * Recupera il valore della proprietà validate.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getValidate() {
        if (validate == null) {
            return TBoolean.NO;
        } else {
            return validate;
        }
    }

    /**
     * Imposta il valore della proprietà validate.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setValidate(TBoolean value) {
        this.validate = value;
    }

}
