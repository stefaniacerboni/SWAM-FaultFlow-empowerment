
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per netType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="netType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{pnml.apromore.org}netNameType">
 *                 &lt;sequence>
 *                   &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="place" type="{pnml.apromore.org}placeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transition" type="{pnml.apromore.org}transitionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="arc" type="{pnml.apromore.org}arcType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="toolspecific" type="{pnml.apromore.org}netToolspecificType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="graphics" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="page" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{pnml.apromore.org}pageType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="referencePlace" type="{pnml.apromore.org}referencePlaceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "netType", namespace = "pnml.apromore.org", propOrder = {
    "name",
    "place",
    "transition",
    "arc",
    "toolspecific",
    "graphics",
    "page",
    "referencePlace"
})
@XmlSeeAlso({
    PageType.class
})
public class NetType {

    @XmlElement(namespace = "pnml.apromore.org")
    protected NetType.Name name;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<PlaceType> place;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<TransitionType> transition;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<ArcType> arc;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<NetToolspecificType> toolspecific;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Object graphics;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<NetType.Page> page;
    @XmlElement(namespace = "pnml.apromore.org")
    protected List<ReferencePlaceType> referencePlace;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "type", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String type;

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link NetType.Name }
     *     
     */
    public NetType.Name getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link NetType.Name }
     *     
     */
    public void setName(NetType.Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the place property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the place property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlaceType }
     * 
     * 
     */
    public List<PlaceType> getPlace() {
        if (place == null) {
            place = new ArrayList<PlaceType>();
        }
        return this.place;
    }

    /**
     * Gets the value of the transition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransitionType }
     * 
     * 
     */
    public List<TransitionType> getTransition() {
        if (transition == null) {
            transition = new ArrayList<TransitionType>();
        }
        return this.transition;
    }

    /**
     * Gets the value of the arc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArcType }
     * 
     * 
     */
    public List<ArcType> getArc() {
        if (arc == null) {
            arc = new ArrayList<ArcType>();
        }
        return this.arc;
    }

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
     * {@link NetToolspecificType }
     * 
     * 
     */
    public List<NetToolspecificType> getToolspecific() {
        if (toolspecific == null) {
            toolspecific = new ArrayList<NetToolspecificType>();
        }
        return this.toolspecific;
    }

    /**
     * Recupera il valore della proprietà graphics.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGraphics() {
        return graphics;
    }

    /**
     * Imposta il valore della proprietà graphics.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGraphics(Object value) {
        this.graphics = value;
    }

    /**
     * Gets the value of the page property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the page property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NetType.Page }
     * 
     * 
     */
    public List<NetType.Page> getPage() {
        if (page == null) {
            page = new ArrayList<NetType.Page>();
        }
        return this.page;
    }

    /**
     * Gets the value of the referencePlace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referencePlace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferencePlace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferencePlaceType }
     * 
     * 
     */
    public List<ReferencePlaceType> getReferencePlace() {
        if (referencePlace == null) {
            referencePlace = new ArrayList<ReferencePlaceType>();
        }
        return this.referencePlace;
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

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{pnml.apromore.org}netNameType">
     *       &lt;sequence>
     *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "text"
    })
    public static class Name
        extends NetNameType
    {

        @XmlElement(namespace = "pnml.apromore.org", required = true)
        protected String text;

        /**
         * Recupera il valore della proprietà text.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getText() {
            return text;
        }

        /**
         * Imposta il valore della proprietà text.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setText(String value) {
            this.text = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{pnml.apromore.org}pageType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Page
        extends PageType
    {


    }

}
