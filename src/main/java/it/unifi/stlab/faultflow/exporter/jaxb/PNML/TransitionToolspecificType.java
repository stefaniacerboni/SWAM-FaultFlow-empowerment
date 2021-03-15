
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per transitionToolspecificType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="transitionToolspecificType">
 *   &lt;complexContent>
 *     &lt;extension base="{pnml.apromore.org}toolspecificType">
 *       &lt;sequence>
 *         &lt;element name="trigger" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{pnml.apromore.org}triggerType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="operator" type="{pnml.apromore.org}operatorType"/>
 *           &lt;element name="subprocess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *         &lt;element name="transitionResource" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{pnml.apromore.org}transitionResourceType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="timeUnit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orientation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{pnml.apromore.org}assign"/>
 *           &lt;element ref="{pnml.apromore.org}invoke"/>
 *           &lt;element ref="{pnml.apromore.org}reply"/>
 *           &lt;element ref="{pnml.apromore.org}wait"/>
 *           &lt;element ref="{pnml.apromore.org}receive"/>
 *           &lt;element ref="{pnml.apromore.org}empty"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transitionToolspecificType", namespace = "pnml.apromore.org", propOrder = {
    "trigger",
    "operator",
    "subprocess",
    "transitionResource",
    "time",
    "timeUnit",
    "orientation",
    "assign",
    "invoke",
    "reply",
    "wait",
    "receive",
    "empty"
})
public class TransitionToolspecificType
    extends ToolspecificType
{

    @XmlElement(namespace = "pnml.apromore.org")
    protected TransitionToolspecificType.Trigger trigger;
    @XmlElement(namespace = "pnml.apromore.org")
    protected OperatorType operator;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Boolean subprocess;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TransitionToolspecificType.TransitionResource transitionResource;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Integer time;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Integer timeUnit;
    @XmlElement(namespace = "pnml.apromore.org")
    protected Integer orientation;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TAssign assign;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TInvoke invoke;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TReply reply;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TWait wait;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TReceive receive;
    @XmlElement(namespace = "pnml.apromore.org")
    protected TEmpty empty;

    /**
     * Recupera il valore della proprietà trigger.
     * 
     * @return
     *     possible object is
     *     {@link TransitionToolspecificType.Trigger }
     *     
     */
    public TransitionToolspecificType.Trigger getTrigger() {
        return trigger;
    }

    /**
     * Imposta il valore della proprietà trigger.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionToolspecificType.Trigger }
     *     
     */
    public void setTrigger(TransitionToolspecificType.Trigger value) {
        this.trigger = value;
    }

    /**
     * Recupera il valore della proprietà operator.
     * 
     * @return
     *     possible object is
     *     {@link OperatorType }
     *     
     */
    public OperatorType getOperator() {
        return operator;
    }

    /**
     * Imposta il valore della proprietà operator.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorType }
     *     
     */
    public void setOperator(OperatorType value) {
        this.operator = value;
    }

    /**
     * Recupera il valore della proprietà subprocess.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubprocess() {
        return subprocess;
    }

    /**
     * Imposta il valore della proprietà subprocess.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubprocess(Boolean value) {
        this.subprocess = value;
    }

    /**
     * Recupera il valore della proprietà transitionResource.
     * 
     * @return
     *     possible object is
     *     {@link TransitionToolspecificType.TransitionResource }
     *     
     */
    public TransitionToolspecificType.TransitionResource getTransitionResource() {
        return transitionResource;
    }

    /**
     * Imposta il valore della proprietà transitionResource.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionToolspecificType.TransitionResource }
     *     
     */
    public void setTransitionResource(TransitionToolspecificType.TransitionResource value) {
        this.transitionResource = value;
    }

    /**
     * Recupera il valore della proprietà time.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTime() {
        return time;
    }

    /**
     * Imposta il valore della proprietà time.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTime(Integer value) {
        this.time = value;
    }

    /**
     * Recupera il valore della proprietà timeUnit.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeUnit() {
        return timeUnit;
    }

    /**
     * Imposta il valore della proprietà timeUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeUnit(Integer value) {
        this.timeUnit = value;
    }

    /**
     * Recupera il valore della proprietà orientation.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrientation() {
        return orientation;
    }

    /**
     * Imposta il valore della proprietà orientation.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrientation(Integer value) {
        this.orientation = value;
    }

    /**
     * Recupera il valore della proprietà assign.
     * 
     * @return
     *     possible object is
     *     {@link TAssign }
     *     
     */
    public TAssign getAssign() {
        return assign;
    }

    /**
     * Imposta il valore della proprietà assign.
     * 
     * @param value
     *     allowed object is
     *     {@link TAssign }
     *     
     */
    public void setAssign(TAssign value) {
        this.assign = value;
    }

    /**
     * Recupera il valore della proprietà invoke.
     * 
     * @return
     *     possible object is
     *     {@link TInvoke }
     *     
     */
    public TInvoke getInvoke() {
        return invoke;
    }

    /**
     * Imposta il valore della proprietà invoke.
     * 
     * @param value
     *     allowed object is
     *     {@link TInvoke }
     *     
     */
    public void setInvoke(TInvoke value) {
        this.invoke = value;
    }

    /**
     * Recupera il valore della proprietà reply.
     * 
     * @return
     *     possible object is
     *     {@link TReply }
     *     
     */
    public TReply getReply() {
        return reply;
    }

    /**
     * Imposta il valore della proprietà reply.
     * 
     * @param value
     *     allowed object is
     *     {@link TReply }
     *     
     */
    public void setReply(TReply value) {
        this.reply = value;
    }

    /**
     * Recupera il valore della proprietà wait.
     * 
     * @return
     *     possible object is
     *     {@link TWait }
     *     
     */
    public TWait getWait() {
        return wait;
    }

    /**
     * Imposta il valore della proprietà wait.
     * 
     * @param value
     *     allowed object is
     *     {@link TWait }
     *     
     */
    public void setWait(TWait value) {
        this.wait = value;
    }

    /**
     * Recupera il valore della proprietà receive.
     * 
     * @return
     *     possible object is
     *     {@link TReceive }
     *     
     */
    public TReceive getReceive() {
        return receive;
    }

    /**
     * Imposta il valore della proprietà receive.
     * 
     * @param value
     *     allowed object is
     *     {@link TReceive }
     *     
     */
    public void setReceive(TReceive value) {
        this.receive = value;
    }

    /**
     * Recupera il valore della proprietà empty.
     * 
     * @return
     *     possible object is
     *     {@link TEmpty }
     *     
     */
    public TEmpty getEmpty() {
        return empty;
    }

    /**
     * Imposta il valore della proprietà empty.
     * 
     * @param value
     *     allowed object is
     *     {@link TEmpty }
     *     
     */
    public void setEmpty(TEmpty value) {
        this.empty = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{pnml.apromore.org}transitionResourceType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TransitionResource
        extends TransitionResourceType
    {


    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{pnml.apromore.org}triggerType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Trigger
        extends TriggerType
    {


    }

}
