package org.example.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

/**
 * The class with spaceMarine's astartesCategory
 */
@XmlEnum(String.class)
public enum AstartesCategory implements Serializable {
    @XmlEnumValue("Scout")
    SCOUT,
    @XmlEnumValue("Inceptor")
    INCEPTOR,
    @XmlEnumValue("tactical")
    TACTICAL,
    @XmlEnumValue("terminator")
    TERMINATOR,
    @XmlEnumValue("helix")
    HELIX;
}