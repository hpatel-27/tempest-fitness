package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Workout extends DomainObject {

    /** ID for the Workout in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** List of exercises completed during a workout */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<UserExercise> userExercises;

    /** Date that the workout was completed */
    private String date;

    /**
     * Default Constructor that requires a date with "2025-01-25" format. It
     * will use the current date to start, which can be updated.
     */
    public Workout () {
        setDate(LocalDate.now().toString());
        userExercises = new ArrayList<>();
    }

    /**
     * Get the list of exercises for the workout
     * @return The list of exercises for the workout
     */
    public List<UserExercise> getExercises () {
        return userExercises;
    }

    /**
     * Gets the exercise matching the one passed
     *
     * @param userExercise
     *            Exercise to retrieve
     * @return Null if exercise isn't in the workout, or the exercise
     * doesn't exist
     */
    public UserExercise getExercise(final UserExercise userExercise) {
        if ( !userExercises.contains(userExercise) ) {
            return null;
        }
        final int index = userExercises.indexOf(userExercise);
        return userExercises.get( index );
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
    public void setDate (String date) {
        this.date = date;
    }

    /**
     * Adds an exercise to the list of exercises. Replaces an existing
     * exercise or adds a new one.
     *
     * @param userExercise
     *            Exercise to add
     */
    public void addExercise ( final UserExercise userExercise) {
        // Check the passed Exercise
        if ( userExercise.getSets() < 0 ) {
            throw new IllegalArgumentException( "Amount of sets must be a positive integer." );
        }
        if ( userExercise.getReps() < 0 ) {
            throw new IllegalArgumentException( "Amount of reps must be a positive integer." );
        }
        if ( userExercise.getWeight() < 0.0 ) {
            throw new IllegalArgumentException( "Weight value must be a positive integer." );
        }
        if ( userExercise.getName() == null || userExercise.getName().isEmpty() ) {
            throw new IllegalArgumentException( "Amount of sets must be a positive integer." );
        }


        if ( userExercises.contains(userExercise) ) {
            throw new IllegalArgumentException( "Exercise already exists. Update the existing exercise.");
        }
        else {
            userExercises.add(userExercise);
        }
    }

    /**
     * Adds an exercise to the list of exercise. Replaces an existing
     * exercise or adds a new one.
     *
     * @param userExercise
     *            Exercise to add
     */
    public void setExercise ( final UserExercise userExercise) {
        if ( userExercise.getSets() < 0 ) {
            throw new IllegalArgumentException( "Set value must be a positive integer" );
        }

        if ( userExercises.contains(userExercise) ) {
            UserExercise updatingUserExercise = userExercises.get( userExercises.indexOf(userExercise) );

            updatingUserExercise.setSets( userExercise.getSets() );
            updatingUserExercise.setReps( userExercise.getReps() );
            updatingUserExercise.setWeight( userExercise.getWeight() );
            updatingUserExercise.setName( userExercise.getName() );
        }
        else {
            userExercises.add(userExercise);
        }
    }

    /**
     * Removes the exercise matching the one that was passed
     * @param userExercise The exercise to retrieve
     * @return True if removed successfully, false if not
     */
    public boolean removeExercise ( final UserExercise userExercise) {
        return userExercises.remove(userExercise);
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
        final List<UserExercise> newUserExercises = workout.getExercises();

        // Delete any exercises which didn't make it over to the updated workout
        for ( int i = 0; i < this.userExercises.size(); ) {
            if ( !newUserExercises.contains( userExercises.get( i ) ) ) {
                userExercises.remove( i );
                // Do NOT increment i here since the new Exercise at index i has not
                // been changed
            }
            else {
                i++;
            }
        }

        // Set each ingredient
        for ( final UserExercise e : workout.getExercises() ) {
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
