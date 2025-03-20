package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.UserExercise;
import com.hpatel.Tempest_Fitness.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to the repository and getting Exercise models
 * from the database.
 */
@Component
@Transactional
public class ExerciseService extends Service<UserExercise, Long> {

    /** The instance of the repository */
    @Autowired
    private ExerciseRepository exerciseRepository;

    /**
     * Returns the repository for the ingredients
     * @return the repository
     */
    @Override
    protected JpaRepository<UserExercise, Long> getRepository() {
        return exerciseRepository;
    }

    /**
     * Find an exercise with the provided name
     *
     * @param name
     *            Name of the exercise to find
     * @return found exercise, null if none
     */
    public UserExercise findByName (final String name ) {
        return exerciseRepository.findByName( name );
    }
}
