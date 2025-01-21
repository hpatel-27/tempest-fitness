package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
     * Empty default constructor
     */
    public Weight() {
        // default constructor
    }

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

    /**
     * Sets the weight of the User
     * @param weight The weight to set
     */
    public void setWeight(double weight) {
        // Check if the user's weight is between 0 and 1000, otherwise it is invalid
        if ( weight < 0.0 || weight > 1000.0 ) {
            throw new IllegalArgumentException("Weight value outside of expected range.");
        }
        this.weight = weight;
    }

    /**
     * Sets the date of the weigh-in
     * @param date Date to set for the weigh-in
     */
    public void setDate(String date) {
        // check if a null string was passed or if the string is empty
        // check if the date is the appropriate length
        if (date == null || date.isBlank() || date.length() != 10 ) {
            throw new IllegalArgumentException("Date of the weigh-in is not valid.");
        }

        this.date = date;
    }

    /**
     * Gets the weight of the user
     * @return The weight of the user for this object
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the date of the weigh-in
     * @return The date of the weigh-in
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the id of the object
     * @return The id of the Weight
     */
    @Override
    public Serializable getId() {
        return id;
    }

    /**
     * Set the ID of the Weight (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /** Converts a Weight object into a string representation */
    @Override
    public String toString() {
        return "Weight{" +
                "date='" + date + '\'' +
                ", weight=" + weight +
                '}';
    }

    /**
     * Considers if two Weights are equal by checking their dates and weights
     * @param o The Weight used for comparison
     * @return True if the dates and weights are the same in both objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight1 = (Weight) o;
        return Double.compare(getWeight(), weight1.getWeight()) == 0 && Objects.equals(getDate(), weight1.getDate());
    }

    /**
     * Hashing the object
     * @return The hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getWeight());
    }

}
