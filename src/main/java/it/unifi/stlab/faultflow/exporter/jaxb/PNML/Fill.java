
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="color" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="gradient-color" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="gradient-rotation" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="images" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "fill", namespace = "pnml.apromore.org")
public class Fill {

    @XmlAttribute(name = "color")
    @XmlSchemaType(name = "anySimpleType")
    protected String color;
    @XmlAttribute(name = "gradient-color")
    @XmlSchemaType(name = "anySimpleType")
    protected String gradientColor;
    @XmlAttribute(name = "gradient-rotation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String gradientRotation;
    @XmlAttribute(name = "images")
    @XmlSchemaType(name = "anyURI")
    protected String images;

    /**
     * Recupera il valore della proprietà color.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Imposta il valore della proprietà color.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Recupera il valore della proprietà gradientColor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradientColor() {
        return gradientColor;
    }

    /**
     * Imposta il valore della proprietà gradientColor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradientColor(String value) {
        this.gradientColor = value;
    }

    /**
     * Recupera il valore della proprietà gradientRotation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradientRotation() {
        return gradientRotation;
    }

    /**
     * Imposta il valore della proprietà gradientRotation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradientRotation(String value) {
        this.gradientRotation = value;
    }

    /**
     * Recupera il valore della proprietà images.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImages() {
        return images;
    }

    /**
     * Imposta il valore della proprietà images.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImages(String value) {
        this.images = value;
    }

}
