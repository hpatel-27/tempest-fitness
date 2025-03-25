package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.UserExercise;
import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.repositories.UserExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Provides access to the repository and getting Exercise models
 * from the database.
 */
@Service
@Transactional
public class UserExerciseService extends CustomService<UserExercise, Long> {

    /** The instance of the repository */
    @Autowired
    private UserExerciseRepository userExerciseRepository;

    /**
     * Returns the repository for the ingredients
     * @return the repository
     */
    @Override
    protected JpaRepository<UserExercise, Long> getRepository() {
        return userExerciseRepository;
    }

    /**
     * Gets all the UserExercises associated with a given Workout
     * @param workout Workout we want to get UserExercises from
     * @return The list of UserExercises
     */
    public List<UserExercise> findByWorkout(Workout workout) {
        return userExerciseRepository.findByWorkout(workout);
    }

    /**
     * Finds the UserExercise with the given id, or null
     * @param id
     *            ID of the object to find
     * @return UserExercise with the given id or null
     */
    public UserExercise findById(Long id) {
        return userExerciseRepository.findById(id).orElse(null);
    }
}
