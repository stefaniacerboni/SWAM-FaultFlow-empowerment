
package it.unifi.stlab.faultflow.exporter.jaxb.PNML;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tRoles.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tRoles">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="myRole"/>
 *     &lt;enumeration value="partnerRole"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tRoles", namespace = "pnml.apromore.org")
@XmlEnum
public enum TRoles {

    @XmlEnumValue("myRole")
    MY_ROLE("myRole"),
    @XmlEnumValue("partnerRole")
    PARTNER_ROLE("partnerRole");
    private final String value;

    TRoles(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TRoles fromValue(String v) {
        for (TRoles c: TRoles.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
