package org.example.data;

import org.example.error.InvalidInputException;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
/**
 * The class with spaceMarine's coordinates
 */
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType(propOrder = {"x", "y"})
public class Coordinates implements Serializable, IValidator {
    @XmlTransient
    private Integer x;
    @XmlTransient
    private Float y;

    public Coordinates() {
    }

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    @XmlElement
    public void setX(Integer x) {
        this.x = x;
    }

    @XmlElement
    public void setY(Float y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;

        if (getX() != null ? !getX().equals(that.getX()) : that.getX() != null) return false;
        return getY() != null ? getY().equals(that.getY()) : that.getY() == null;
    }

    @Override
    public int hashCode() {
        int result = getX() != null ? getX().hashCode() : 0;
        result = 31 * result + (getY() != null ? getY().hashCode() : 0);
        return result;
    }

    @Override
    public boolean validate() {
        if (this.x == null ||
            this.y == null ||
            this.x <= -595){
            return false;
        }else{
            return true;
        }
    }
}


