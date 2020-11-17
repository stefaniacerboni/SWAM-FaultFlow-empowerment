
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per netToolspecificType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="netToolspecificType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}toolspecificType">
 *       &lt;sequence>
 *         &lt;element name="bounds" type="{pnml.apromore.org}graphicsSimpleType" minOccurs="0"/>
 *         &lt;element name="treeWidth" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="verticalLayout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="resources" type="{pnml.apromore.org}resourcesType" minOccurs="0"/>
 *         &lt;element name="simulations" type="{pnml.apromore.org}simulationsType" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}partnerLinks" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}variables" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "netToolspecificType", namespace = "pnml.apromore.org", propOrder = {
    "bounds",
    "treeWidth",
    "verticalLayout",
    "resources",
    "simulations",
    "partnerLinks",
    "variables"
})
public class NetToolspecificType
    extends ToolspecificType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected GraphicsSimpleType bounds;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Integer treeWidth;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Boolean verticalLayout;
    @XmlElement(namespace = "pnml.apromore.org")
    protected ResourcesType resources;
    @XmlElement(namespace = "pnml.apromore.org")
    protected SimulationsType simulations;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TPartnerLinks partnerLinks;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TVariables variables;

    /**
     * Recupera il valore della proprietà bounds.
     * 
     * @return
     *     possible object is
     *     {@link GraphicsSimpleType }
     *     
     */
    public GraphicsSimpleType getBounds() {
        return bounds;
    }

    /**
     * Imposta il valore della proprietà bounds.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphicsSimpleType }
     *     
     */
    public void setBounds(GraphicsSimpleType value) {
        this.bounds = value;
    }

    /**
     * Recupera il valore della proprietà treeWidth.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTreeWidth() {
        return treeWidth;
    }

    /**
     * Imposta il valore della proprietà treeWidth.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTreeWidth(Integer value) {
        this.treeWidth = value;
    }

    /**
     * Recupera il valore della proprietà verticalLayout.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVerticalLayout() {
        return verticalLayout;
    }

    /**
     * Imposta il valore della proprietà verticalLayout.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerticalLayout(Boolean value) {
        this.verticalLayout = value;
    }

    /**
     * Recupera il valore della proprietà resources.
     * 
     * @return
     *     possible object is
     *     {@link ResourcesType }
     *     
     */
    public ResourcesType getResources() {
        return resources;
    }

    /**
     * Imposta il valore della proprietà resources.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourcesType }
     *     
     */
    public void setResources(ResourcesType value) {
        this.resources = value;
    }

    /**
     * Recupera il valore della proprietà simulations.
     * 
     * @return
     *     possible object is
     *     {@link SimulationsType }
     *     
     */
    public SimulationsType getSimulations() {
        return simulations;
    }

    /**
     * Imposta il valore della proprietà simulations.
     * 
     * @param value
     *     allowed object is
     *     {@link SimulationsType }
     *     
     */
    public void setSimulations(SimulationsType value) {
        this.simulations = value;
    }

    /**
     * Recupera il valore della proprietà partnerLinks.
     * 
     * @return
     *     possible object is
     *     {@link TPartnerLinks }
     *     
     */
    public TPartnerLinks getPartnerLinks() {
        return partnerLinks;
    }

    /**
     * Imposta il valore della proprietà partnerLinks.
     * 
     * @param value
     *     allowed object is
     *     {@link TPartnerLinks }
     *     
     */
    public void setPartnerLinks(TPartnerLinks value) {
        this.partnerLinks = value;
    }

    /**
     * Recupera il valore della proprietà variables.
     * 
     * @return
     *     possible object is
     *     {@link TVariables }
     *     
     */
    public TVariables getVariables() {
        return variables;
    }

    /**
     * Imposta il valore della proprietà variables.
     * 
     * @param value
     *     allowed object is
     *     {@link TVariables }
     *     
     */
    public void setVariables(TVariables value) {
        this.variables = value;
    }

}
