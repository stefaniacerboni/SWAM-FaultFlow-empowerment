
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per simulationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="simulationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simulationname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="simulationdate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="transitionsequence" type="{pnml.apromore.org}transitionsequenceType"/>
 *         &lt;element name="netFingerprint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simulationType", namespace = "pnml.apromore.org", propOrder = {
    "simulationname",
    "simulationdate",
    "transitionsequence",
    "netFingerprint"
})
public class SimulationType {

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected String simulationname;
    @XmlElement(namespace = "pnml.apromore.org", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar simulationdate;
    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected TransitionsequenceType transitionsequence;
    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected String netFingerprint;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Recupera il valore della proprietà simulationname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimulationname() {
        return simulationname;
    }

    /**
     * Imposta il valore della proprietà simulationname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimulationname(String value) {
        this.simulationname = value;
    }

    /**
     * Recupera il valore della proprietà simulationdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSimulationdate() {
        return simulationdate;
    }

    /**
     * Imposta il valore della proprietà simulationdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSimulationdate(XMLGregorianCalendar value) {
        this.simulationdate = value;
    }

    /**
     * Recupera il valore della proprietà transitionsequence.
     * 
     * @return
     *     possible object is
     *     {@link TransitionsequenceType }
     *     
     */
    public TransitionsequenceType getTransitionsequence() {
        return transitionsequence;
    }

    /**
     * Imposta il valore della proprietà transitionsequence.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionsequenceType }
     *     
     */
    public void setTransitionsequence(TransitionsequenceType value) {
        this.transitionsequence = value;
    }

    /**
     * Recupera il valore della proprietà netFingerprint.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetFingerprint() {
        return netFingerprint;
    }

    /**
     * Imposta il valore della proprietà netFingerprint.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetFingerprint(String value) {
        this.netFingerprint = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
