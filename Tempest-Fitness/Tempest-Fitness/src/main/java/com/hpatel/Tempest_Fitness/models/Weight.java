package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Weight extends DomainObject {

    /** ID for the Exercise in the database */
    @Id
    @GeneratedValue
    private long id;

    /** Date of the weigh-in */
    private String date;


    private double weight;

    public Weight(final String date, final double weight) {
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
