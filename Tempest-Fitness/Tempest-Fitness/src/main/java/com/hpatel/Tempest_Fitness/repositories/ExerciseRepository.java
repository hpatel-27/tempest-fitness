package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * Finds an Exercise based on its name
     * @param name The name of the exercise
     * @return The Exercise object
     */
    Exercise findByName(String name);
}
