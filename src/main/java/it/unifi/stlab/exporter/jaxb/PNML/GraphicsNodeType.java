
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per graphicsNodeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="graphicsNodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="position" type="{pnml.apromore.org}positionType"/>
 *         &lt;element name="dimension" type="{pnml.apromore.org}dimensionType" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}fill" minOccurs="0"/>
 *         &lt;element ref="{pnml.apromore.org}line" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "graphicsNodeType", namespace = "pnml.apromore.org", propOrder = {
    "position",
    "dimension",
    "fill",
    "line"
})
public class GraphicsNodeType {

    @XmlElement(namespace = "pnml.apromore.org", required = true)
    protected PositionType position;
    @XmlElement(namespace = "pnml.apromore.org")
    protected DimensionType dimension;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Fill fill;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Line line;

    /**
     * Recupera il valore della proprietà position.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * Imposta il valore della proprietà position.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setPosition(PositionType value) {
        this.position = value;
    }

    /**
     * Recupera il valore della proprietà dimension.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getDimension() {
        return dimension;
    }

    /**
     * Imposta il valore della proprietà dimension.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setDimension(DimensionType value) {
        this.dimension = value;
    }

    /**
     * Recupera il valore della proprietà fill.
     * 
     * @return
     *     possible object is
     *     {@link Fill }
     *     
     */
    public Fill getFill() {
        return fill;
    }

    /**
     * Imposta il valore della proprietà fill.
     * 
     * @param value
     *     allowed object is
     *     {@link Fill }
     *     
     */
    public void setFill(Fill value) {
        this.fill = value;
    }

    /**
     * Recupera il valore della proprietà line.
     * 
     * @return
     *     possible object is
     *     {@link Line }
     *     
     */
    public Line getLine() {
        return line;
    }

    /**
     * Imposta il valore della proprietà line.
     * 
     * @param value
     *     allowed object is
     *     {@link Line }
     *     
     */
    public void setLine(Line value) {
        this.line = value;
    }

}
