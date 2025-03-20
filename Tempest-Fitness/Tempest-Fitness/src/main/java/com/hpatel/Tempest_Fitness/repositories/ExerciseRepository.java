package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository is used to find the specific Exercise models from the database.
 * Spring will generate appropriate code with JPA.
 */
public interface ExerciseRepository extends JpaRepository<UserExercise, Long> {

    /**
     * Finds an Exercise object with the provided name.
     *
     * @param name
     *            Name of the exercise
     * @return Found exercise, null if none.
     */
    UserExercise findByName (String name );
}
