package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Workout extends DomainObject {

    /** ID for the Workout in the database */
    @Id
    @GeneratedValue
    private long id;

    /** List of exercises completed during a workout */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<Exercise> exercises;

    /** Date that the workout was completed */
    private String date;

    /**
     * Constructor that requires a date with "2025-01-25" format
     * @param date The date to set for the workout
     */
    public Workout (String date) {
        setDate(date);
        exercises = new ArrayList<>();
    }

    /**
     * Get the list of exercises for the workout
     * @return The list of exercises for the workout
     */
    public List<Exercise> getExercises () {
        return exercises;
    }

    /**
     * Gets the exercise matching the one passed
     *
     * @param exercise
     *            Exercise to retrieve
     * @return Null if exercise isn't in the workout, or the exercise
     * doesn't exist
     */
    public Exercise getExercise( final Exercise exercise ) {
        if ( !exercises.contains( exercise ) ) {
            return null;
        }
        final int index = exercises.indexOf( exercise );
        return exercises.get( index );
    }

    /**
     * Gets the date of the Workout
     * @return Date of the workout
     */
    public String getDate () {
        return date;
    }

    /**
     * Sets the date of the workout
     * @param date The new date of the workout
     */
    private void setDate (String date) {
        this.date = date;
    }

    /**
     * Adds an exercise to the list of exercises. Replaces an existing
     * exercise or adds a new one.
     *
     * @param exercise
     *            Exercise to add
     */
    public void addExercise ( final Exercise exercise ) {
        // Check the passed Exercise
        if ( exercise.getSets() < 0 ) {
            throw new IllegalArgumentException( "Amount of sets must be a positive integer." );
        }
        if ( exercise.getReps() < 0 ) {
            throw new IllegalArgumentException( "Amount of reps must be a positive integer." );
        }
        if ( exercise.getWeight() < 0.0 ) {
            throw new IllegalArgumentException( "Weight value must be a positive integer." );
        }
        if ( exercise.getName() == null || exercise.getName().isEmpty() ) {
            throw new IllegalArgumentException( "Amount of sets must be a positive integer." );
        }


        if ( exercises.contains( exercise ) ) {
            throw new IllegalArgumentException( "Exercise already exists. Update the existing exercise.");
        }
        else {
            exercises.add( exercise );
        }
    }

    /**
     * Adds an exercise to the list of exercise. Replaces an existing
     * exercise or adds a new one.
     *
     * @param exercise
     *            Exercise to add
     */
    public void setExercise ( final Exercise exercise ) {
        if ( exercise.getSets() < 0 ) {
            throw new IllegalArgumentException( "Set value must be a positive integer" );
        }

        if ( exercises.contains( exercise ) ) {
            Exercise updatingExercise = exercises.get( exercises.indexOf( exercise ) );

            updatingExercise.setSets( exercise.getSets() );
            updatingExercise.setReps( exercise.getReps() );
            updatingExercise.setWeight( exercise.getWeight() );
            updatingExercise.setName( exercise.getName() );
        }
        else {
            exercises.add( exercise );
        }
    }

    /**
     * Removes the exercise matching the one that was passed
     * @param exercise The exercise to retrieve
     * @return True if removed successfully, false if not
     */
    public boolean removeExercise ( final Exercise exercise ) {
        return exercises.remove( exercise );
    }

    /**
     * Updates a Workout to match a given Workout.
     *
     * @param workout
     *            Workout to update this one to
     */
    public void updateWorkout ( final Workout workout ) {
        // Set the Date
        this.setDate( workout.getDate() );

        // Get the list of new exercises
        final List<Exercise> newExercises = workout.getExercises();

        // Delete any exercises which didn't make it over to the updated workout
        for ( int i = 0; i < this.exercises.size(); ) {
            if ( !newExercises.contains( exercises.get( i ) ) ) {
                exercises.remove( i );
                // Do NOT increment i here since the new Exercise at index i has not
                // been changed
            }
            else {
                i++;
            }
        }

        // Set each ingredient
        for ( final Exercise e : workout.getExercises() ) {
            this.setExercise( e );
        }
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @SuppressWarnings( "unused")
    private void setId ( final Long id ) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(getExercises(), workout.getExercises()) && Objects.equals(getDate(), workout.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExercises(), getDate());
    }


}
