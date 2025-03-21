package com.hpatel.Tempest_Fitness.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Exercise extends DomainObject {

    /** ID for the UserExercise in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Name of the exercise */
    private String name;

    /** Type of exercise (Olympic, Strength, Plyometric, etc.) */
    private String type;

    /** Muscle(s) that are used in the exercise */
    private String muscle;

    /** Equipment necessary to complete exercise (Barbell, dumbbell, none, etc.) */
    private String equipment;

    /** Difficulty of the exercise (Beginner, Intermediate, Advanced */
    private String difficulty;

    /** Instructions to complete the exercise */
    @Column(length = 1000)
    private String instructions;

    /**
     * Default constructor
     */
    public Exercise() {
        // empty default constructor
    }

    public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions) {
        setName(name);
        setType(type);
        setMuscle(muscle);
        setEquipment(equipment);
        setDifficulty(difficulty);
        setInstructions(instructions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Returns the id for the UserExercise
     *
     * @return the id
     */
    @Override
    public Serializable getId() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(getName(), exercise.getName()) && Objects.equals(getType(), exercise.getType()) && Objects.equals(getMuscle(), exercise.getMuscle()) && Objects.equals(getEquipment(), exercise.getEquipment()) && Objects.equals(getDifficulty(), exercise.getDifficulty()) && Objects.equals(getInstructions(), exercise.getInstructions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getMuscle(), getEquipment(), getDifficulty(), getInstructions());
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", muscle='" + muscle + '\'' +
                ", equipment='" + equipment + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
