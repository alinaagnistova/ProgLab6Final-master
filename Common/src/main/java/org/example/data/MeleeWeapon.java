package org.example.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

/**
 * The class with spaceMarine's MeleeWeapon
 */
@XmlEnum(String.class)
public enum MeleeWeapon implements Serializable {
    @XmlEnumValue("chain")
    CHAIN_SWORD,
    @XmlEnumValue("power")
    POWER_SWORD,
    @XmlEnumValue("chain")
    CHAIN_AXE,
    @XmlEnumValue("reaper")
    MANREAPER,
    @XmlEnumValue("claw")
    LIGHTING_CLAW;
}