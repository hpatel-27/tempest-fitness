package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to the repository and getting Workout models
 * from the database.
 */
@Component
@Transactional
public class WorkoutService extends Service<Workout, Long> {

    /** The instance of the repository */
    @Autowired
    private WorkoutRepository workoutRepository;

    /**
     * Provides access to the repository
     * @return The UserRepository
     */
    @Override
    protected JpaRepository<Workout, Long> getRepository() {
        return workoutRepository;
    }

    /**
     * Finds a Workout model by their date
     * @param date The date to use to find a Workout
     * @return The found Workout or null
     */
    public Workout findByDate (final String date ) {
        return workoutRepository.findByDate( date );
    }
}
