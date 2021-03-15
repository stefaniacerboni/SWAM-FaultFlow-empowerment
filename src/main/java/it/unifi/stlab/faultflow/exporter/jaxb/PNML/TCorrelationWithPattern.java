
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tCorrelationWithPattern complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tCorrelationWithPattern">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tCorrelation">
 *       &lt;attribute name="pattern" type="{pnml.apromore.org}tPattern" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationWithPattern", namespace = "pnml.apromore.org")
public class TCorrelationWithPattern
    extends TCorrelation
{

    @XmlAttribute(name = "pattern")
    protected TPattern pattern;

    /**
     * Recupera il valore della proprietà pattern.
     * 
     * @return
     *     possible object is
     *     {@link TPattern }
     *     
     */
    public TPattern getPattern() {
        return pattern;
    }

    /**
     * Imposta il valore della proprietà pattern.
     * 
     * @param value
     *     allowed object is
     *     {@link TPattern }
     *     
     */
    public void setPattern(TPattern value) {
        this.pattern = value;
    }

}
