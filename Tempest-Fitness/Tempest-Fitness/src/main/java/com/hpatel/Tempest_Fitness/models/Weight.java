package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Weight object that allows a user to track their weight for a provided date
 *
 * @author Harsh Patel
 */
@Entity
public class Weight extends DomainObject {

    /** ID for the Exercise in the database */
    @Id
    @GeneratedValue
    private long id;

    /** Date of the weigh-in */
    private String date;

    /** Weight of the user */
    private double weight;

    /**
     * Construct a weight object with the current date and the given weight
     * @param weight The weight to set for the object
     */
    public Weight(final double weight) {
        // When the object is created, lets have it use the current date, but allow
        // for the date to be changed and accessed
        setDate(LocalDate.now().toString());
        setWeight(weight);
    }

    /**
     * Construct a weight object with the current date and the given weight
     * @param date The date to set for the weight
     * @param weight The weight to set for the object
     */
    public Weight(final String date, final double weight) {
        // When the object is created, lets have it use the current date, but allow
        // for the date to be changed and accessed
        setDate(date);
        setWeight(weight);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight1 = (Weight) o;
        return Double.compare(getWeight(), weight1.getWeight()) == 0 && Objects.equals(getDate(), weight1.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getWeight());
    }

}
