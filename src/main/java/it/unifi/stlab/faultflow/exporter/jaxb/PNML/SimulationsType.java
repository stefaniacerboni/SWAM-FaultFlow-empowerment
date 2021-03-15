
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Simulation Types
 * 
 * <p>Classe Java per simulationsType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="simulationsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simulation" type="{pnml.apromore.org}simulationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simulationsType", namespace = "pnml.apromore.org", propOrder = {
    "simulation"
})
public class SimulationsType {

    @XmlElement(namespace = "pnml.apromore.org")
    protected List<SimulationType> simulation;

    /**
     * Gets the value of the simulation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the simulation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSimulation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimulationType }
     * 
     * 
     */
    public List<SimulationType> getSimulation() {
        if (simulation == null) {
            simulation = new ArrayList<SimulationType>();
        }
        return this.simulation;
    }

}
