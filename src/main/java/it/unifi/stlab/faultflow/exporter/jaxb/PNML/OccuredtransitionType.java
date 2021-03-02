
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per occuredtransitionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="occuredtransitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="transitionID" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occuredtransitionType", namespace = "pnml.apromore.org")
public class OccuredtransitionType {

    @XmlAttribute(name = "transitionID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object transitionID;

    /**
     * Recupera il valore della proprietà transitionID.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTransitionID() {
        return transitionID;
    }

    /**
     * Imposta il valore della proprietà transitionID.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTransitionID(Object value) {
        this.transitionID = value;
    }

}
