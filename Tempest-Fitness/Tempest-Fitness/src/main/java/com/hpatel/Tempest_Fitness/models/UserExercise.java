package com.hpatel.Tempest_Fitness.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * UserExercise object that is intended to be added to a workout and holds
 * the user's activity for the exercise.
 * An Exercise is tied to the database using Hibernate libraries.
 * See ExerciseRepository and ExerciseService for the other pieces
 * used for database support.
 */
@Entity
public class UserExercise extends DomainObject {

    /** ID for the UserExercise in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    /** The number of sets completed for the exercise */
    private int sets;

    /** The number of reps completed for the exercise per set */
    private int reps;

    /** The weight at which the exercise was completed */
    private double weight;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    @JsonBackReference
    private Workout workout;

    /**
     * Default constructor
     */
    public UserExercise() {
        // empty default constructor
    }

    /**
     * Constructor with all the specified fields (name, sets, reps, weight)
     * @param exercise  Exercise associated with the object
     * @param sets Sets completed for the exercise
     * @param reps Reps completed per set
     * @param weight Weight used for the exercise
     */
    public UserExercise(final Exercise exercise, final int sets, final int reps, final double weight, Workout workout) {
        setExercise(exercise);
        setSets(sets);
        setReps(reps);
        setWeight(weight);
        setWorkout(workout);
    }

    /**
     * Returns the id for the UserExercise
     *
     * @return the id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the UserExercise (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId(final Long id) {
        this.id = id;
    }

    /**
     * Sets the number of sets completed for the exercise
     * @param sets The sets to set
     */
    public void setSets(int sets) {
        if ( sets < 1 || sets > 20 ) {
            throw new IllegalArgumentException("Sets value outside of expected range.");
        }
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
        if ( reps < 1 || reps > 100 ) {
            throw new IllegalArgumentException("Reps value outside of expected range.");
        }
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
        if ( weight < 0.0 || weight > 500.0 ) {
            throw new IllegalArgumentException("Weight value outside of expected range.");
        }
        this.weight = weight;
    }

    /**
     * Gets the weight used for the exercise
     * @return The weight used for the exercise
     */
    public double getWeight() {
        return weight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    /**
     * Convenient method to update all the user's performance metrics for the exercise
     * @param sets Sets completed for the exercise
     * @param reps Reps completed for the exercise
     * @param weight Weight used for the exercise
     */
    public void updatePerformance(int sets, int reps, double weight) {
        setSets(sets);
        setReps(reps);
        setWeight(weight);
    }

    /** Summarizes the object as a string */
    public String getSummary() {
        return String.format("%s - %d sets x %d reps @ %.2f lbs",
                exercise.getName(), sets, reps, weight);
    }

    /**
     * Compare two UserExercises for equality
     * @param o The object to compare against
     * @return True if the other object has the same Exercise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserExercise that = (UserExercise) o;
        return Objects.equals(getExercise(), that.getExercise());
    }

    /** Hashcode method override */
    @Override
    public int hashCode() {
        return Objects.hash(getExercise());
    }

    /**
     * Converts the object to a String
     * @return The string representation of an Exercise
     */
    @Override
    public String toString() {
        return "UserExercise{" +
                "exercise=" + exercise.getName() +
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
