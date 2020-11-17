
package it.unifi.stlab.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tInitiate.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tInitiate">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="yes"/>
 *     &lt;enumeration value="join"/>
 *     &lt;enumeration value="no"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tInitiate", namespace = "pnml.apromore.org")
@XmlEnum
public enum TInitiate {

    @XmlEnumValue("yes")
    YES("yes"),
    @XmlEnumValue("join")
    JOIN("join"),
    @XmlEnumValue("no")
    NO("no");
    private final String value;

    TInitiate(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TInitiate fromValue(String v) {
        for (TInitiate c: TInitiate.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
