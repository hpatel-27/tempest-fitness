package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class Exercise extends DomainObject {
    /** ID for the Exercise in the database */
    @Id
    @GeneratedValue
    private long id;

    /** Name of the exercise */
    private String name;

    /** The number of sets completed for the exercise */
    private int sets;

    /** The number of reps completed for the exercise */
    private int reps;

    /** The weight at which the exercise was completed */
    private double weight;

//    public Exercise () {
//        // empty default constructor
//    }

    public Exercise (final String name, final int sets, final int reps, final double weight) {
        setName(name);
        setSets(sets);
        setReps(reps);
        setWeight(weight);

    }

    /**
     * Returns the id for the ingredient
     *
     * @return the id
     */
    @Override
    public Serializable getId () {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getSets() {
        return sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return getSets() == exercise.getSets() && getReps() == exercise.getReps() && Double.compare(getWeight(), exercise.getWeight()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSets(), getReps(), getWeight());
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
