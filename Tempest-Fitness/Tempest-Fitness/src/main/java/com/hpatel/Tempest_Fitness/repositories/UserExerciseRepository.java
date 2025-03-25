package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.UserExercise;
import com.hpatel.Tempest_Fitness.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository is used to find the specific UserExercise models from the database.
 * Spring will generate appropriate code with JPA.
 */
@Repository
public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {

    /**
     * Find all the UserExercises associated with the given Workout
     * @param workout Workout we want to get the UserExercises for
     * @return The list of UserExercises associated with the Workout
     */
    List<UserExercise> findByWorkout(Workout workout);
}
