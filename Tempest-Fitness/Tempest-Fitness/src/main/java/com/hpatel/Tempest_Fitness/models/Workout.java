package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Workout extends DomainObject {

    /** ID for the Workout in the database */
    @Id
    @GeneratedValue
    private long id;

    private List<Exercise> exercises;

    private String date;

    public Workout (String date) {
        setDate(date);
        exercises = new ArrayList<>();
    }

    public List<Exercise> getExercise () {
        return exercises;
    }

    public void setExercise (List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(getExercise(), workout.getExercise()) && Objects.equals(getDate(), workout.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExercise(), getDate());
    }


}
