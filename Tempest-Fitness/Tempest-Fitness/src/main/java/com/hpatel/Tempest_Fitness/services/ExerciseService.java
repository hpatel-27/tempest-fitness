package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.Exercise;
import com.hpatel.Tempest_Fitness.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExerciseService extends CustomService<Exercise, Long> {

    /** Instance of the repository */
    @Autowired
    private ExerciseRepository exerciseRepository;

    /**
     * Provides access to the repository
     * @return The UserRepository
     */
    @Override
    protected JpaRepository<Exercise, Long> getRepository() {
        return null;
    }

    /**
     * Finds an Exercise based on its name
     * @param name The name of the exercise
     * @return The Exercise object
     */
    public Exercise findByName(String name) {
        return exerciseRepository.findByName(name);
    }
}
