package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exercise object that is intended to be added to a workout.
 * An Exercise is tied to the database using Hibernate libraries.
 * See ExerciseRepository and ExerciseService for the other pieces
 * used for database support.
 */
@Entity
public class Exercise extends DomainObject {
    /** ID for the Exercise in the database */
    @Id
    @GeneratedValue
    private long id;

    /** Name of the exercise */
    private String name;

    /** The number of sets completed for the exercise */
    private int sets;

    /** The number of reps completed for the exercise per set */
    private int reps;

    /** The weight at which the exercise was completed */
    private double weight;

    /**
     * Default constructor
     */
    public Exercise () {
        // empty default constructor
    }

    /**
     * Constructor with all the specified fields (name, sets, reps, weight)
     * @param name Name of the exercise
     * @param sets Sets completed for the exercise
     * @param reps Reps completed per set
     * @param weight Weight used for the exercise
     */
    public Exercise(final String name, final int sets, final int reps, final double weight) {
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
    public Serializable getId() {
        return id;
    }

    /**
     * Set the ID of the Exercise (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId(final Long id) {
        this.id = id;
    }

    /**
     * Sets the name of the exercise
     * @param name Name of the
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the exercise
     * @return Name of the exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the number of sets completed for the exercise
     * @param sets The sets to set
     */
    public void setSets(int sets) {
        this.sets = sets;
    }

    /**
     * Gets the sets value for the Exercise object
     * @return The number of sets completed for the exercise
     */
    public int getSets() {
        return sets;
    }

    /**
     * Sets the reps value for the Exercise
     * @param reps Reps completed for the exercise per set
     */
    public void setReps(int reps) {
        this.reps = reps;
    }

    /**
     * Gets the reps completed for the exercise per set
     * @return Reps completed
     */
    public int getReps() {
        return reps;
    }

    /**
     * Sets the weight for the exercise
     * @param weight The weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight used for the exercise
     * @return The weight used for the exercise
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Compare the name and weight to consider equality, this way if the user
     * tries to add two of the same exercise (same name and weight they might as
     * well update one of them so there isn't a duplicate
     * @param o The object to compare against
     * @return True if the other object has the same name and weight values
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return getName().equals(exercise.getName()) && Double.compare(getWeight(), exercise.getWeight()) == 0;
    }

    /** Hashcode method override */
    @Override
    public int hashCode() {
        return Objects.hash(getSets(), getReps(), getWeight());
    }

    /**
     * Converts the object to a String
     * @return The string representation of an Exercise
     */
    @Override
    public String toString() {
        return "Exercise{" +
                "name=" + name +
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
