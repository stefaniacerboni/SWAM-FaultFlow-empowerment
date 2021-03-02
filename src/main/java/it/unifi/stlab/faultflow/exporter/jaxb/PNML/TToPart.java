
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per tToPart complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tToPart">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}tExtensibleElements">
 *       &lt;attribute name="part" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="fromVariable" use="required" type="{pnml.apromore.org}BPELVariableName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tToPart", namespace = "pnml.apromore.org")
public class TToPart
    extends TExtensibleElements
{

    @XmlAttribute(name = "part", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String part;
    @XmlAttribute(name = "fromVariable", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String fromVariable;

    /**
     * Recupera il valore della proprietà part.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPart() {
        return part;
    }

    /**
     * Imposta il valore della proprietà part.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPart(String value) {
        this.part = value;
    }

    /**
     * Recupera il valore della proprietà fromVariable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromVariable() {
        return fromVariable;
    }

    /**
     * Imposta il valore della proprietà fromVariable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromVariable(String value) {
        this.fromVariable = value;
    }

}
