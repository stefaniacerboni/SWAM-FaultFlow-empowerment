
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per tCorrelation complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tCorrelation">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;attribute name="set" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="initiate" type="{pnml.apromore.org}tInitiate" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelation", namespace = "pnml.apromore.org")
@XmlSeeAlso({
    TCorrelationWithPattern.class
})
public class TCorrelation
    extends TExtensibleElements
{

    @XmlAttribute(name = "set", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String set;
    @XmlAttribute(name = "initiate")
    protected TInitiate initiate;

    /**
     * Recupera il valore della proprietà set.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSet() {
        return set;
    }

    /**
     * Imposta il valore della proprietà set.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSet(String value) {
        this.set = value;
    }

    /**
     * Recupera il valore della proprietà initiate.
     * 
     * @return
     *     possible object is
     *     {@link TInitiate }
     *     
     */
    public TInitiate getInitiate() {
        if (initiate == null) {
            return TInitiate.NO;
        } else {
            return initiate;
        }
    }

    /**
     * Imposta il valore della proprietà initiate.
     * 
     * @param value
     *     allowed object is
     *     {@link TInitiate }
     *     
     */
    public void setInitiate(TInitiate value) {
        this.initiate = value;
    }

}
