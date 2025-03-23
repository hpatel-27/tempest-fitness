package com.hpatel.Tempest_Fitness.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Table(name = "workout", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "user_id"}))
@Entity
public class Workout extends DomainObject {

    /** ID for the Workout in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** List of exercises completed during a workout */
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<UserExercise> userExercises;

    /** Date that the workout was completed */
    private String date;

    /** User foreign key */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Default constructor
     */
    public Workout() {
        // default constructor
    }

    /**
     * Constructor that will use the current date to start, which can be updated.
     * Makes an empty list of userExercises. Sets the associated user.
     */
    public Workout(User user) {
        setDate(LocalDate.now().toString());
        userExercises = new ArrayList<>();
        setUser(user);
    }

    /**
     * Constructor that requires a date with "2025-01-25" format. It
     * will use the current date to start, which can be updated. Makes
     * an empty list of userExercises.
     */
    public Workout(String date, User user) {
        setDate(date);
        userExercises = new ArrayList<>();
        setUser(user);
    }

    /**
     * Constructor that requires a date with "2025-01-25" format. It
     * will use the current date to start, which can be updated. Makes
     * an empty list of userExercises.
     */
    public Workout(String date, List<UserExercise> userExercises, User user) {
        setDate(date);
        this.userExercises = userExercises;
        setUser(user);
    }

    @Override
    public Long getId() {
        return id;
    }

    @SuppressWarnings( "unused")
    private void setId ( final Long id ) {
        this.id = id;
    }


    /**
     * Get the list of exercises for the workout
     * @return The list of exercises for the workout
     */
    public List<UserExercise> getUserExercises () {
        return Collections.unmodifiableList(userExercises);
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
        // check if a null string was passed or if the string is empty
        // check if the date is the appropriate length
        if (date == null || date.isBlank() || date.length() != 10 ) {
            throw new IllegalArgumentException("Date of the weigh-in is not valid.");
        }
        this.date = date;
    }

    /**
     * Get the user associated with this workout
     * @return User the user with this workout
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user for the workout
     * @param user The user for this workout
     */
    public void setUser(User user) {
        if (user == null ) {
            throw new IllegalArgumentException("User was not provided with the Weight.");
        }
        if (user.getUsername() == null) {
            throw new NullPointerException("Username was not provided.");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password was not provided.");
        }
        if (user.getRole() == null) {
            throw new NullPointerException("Role was not provided.");
        }

        this.user = user;
    }

    /**
     * Adds an exercise to the list of exercise. Replaces an existing
     * exercise or adds a new one.
     *
     * @param userExercise
     *            Exercise to add
     */
    public void addOrUpdateExercise ( final UserExercise userExercise) {
        if (userExercise == null) {
            throw new IllegalArgumentException("User did not provided an exercise.");
        }
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
        if ( userExercise.getExercise() == null ) {
            throw new IllegalArgumentException( "Must have an associated Exercise object." );
        }

        if ( userExercises.contains(userExercise) ) {
            UserExercise updatingUserExercise = userExercises.get( userExercises.indexOf(userExercise) );

            updatingUserExercise.setSets( userExercise.getSets() );
            updatingUserExercise.setReps( userExercise.getReps() );
            updatingUserExercise.setWeight( userExercise.getWeight() );
            updatingUserExercise.setExercise( userExercise.getExercise() );
            updatingUserExercise.setWorkout(this);
        }
        else {
            userExercise.setWorkout(this);
            userExercises.add(userExercise);
        }
    }

    /**
     * Removes the exercise matching the one that was passed
     * @param userExercise The exercise to retrieve
     * @return True if removed successfully, false if not
     */
    public boolean removeExercise ( final UserExercise userExercise) {
        if (userExercise == null) {
            throw new IllegalArgumentException("UserExercise must be provided to delete.");
        }
        return userExercises.remove(userExercise);
    }

    /**
     * Updates a Workout to match a given Workout.
     *
     * @param workout
     *            Workout to update this one to
     */
    public void updateWorkout(final Workout workout) {
        // Set the Date
        this.setDate(workout.getDate());

        // Create a new list for the updated exercises
        List<UserExercise> updatedExercises = new ArrayList<>();
        
        // Process all exercises from the new workout
        for (UserExercise newExercise : workout.getUserExercises()) {
            boolean found = false;
            // Try to find and update existing exercise
            for (UserExercise existingExercise : this.userExercises) {
                if (existingExercise.getExercise().getId().equals(newExercise.getExercise().getId())) {
                    existingExercise.setSets(newExercise.getSets());
                    existingExercise.setReps(newExercise.getReps());
                    existingExercise.setWeight(newExercise.getWeight());
                    updatedExercises.add(existingExercise);
                    found = true;
                    break;
                }
            }
            
            // If not found, add the new exercise
            if (!found) {
                UserExercise brandNewExercise = new UserExercise();
                brandNewExercise.setExercise(newExercise.getExercise());
                brandNewExercise.setSets(newExercise.getSets());
                brandNewExercise.setReps(newExercise.getReps());
                brandNewExercise.setWeight(newExercise.getWeight());
                brandNewExercise.setWorkout(this);
                updatedExercises.add(brandNewExercise);
            }
        }

        // Replace all exercises - orphanRemoval will handle cleanup
        this.userExercises.clear();
        this.userExercises.addAll(updatedExercises);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(getUserExercises(), workout.getUserExercises()) && Objects.equals(getDate(), workout.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserExercises(), getDate());
    }


}
