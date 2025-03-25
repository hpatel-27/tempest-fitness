package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Provides access to the repository and getting Workout models
 * from the database.
 */
@Service
@Transactional
public class WorkoutService extends CustomService<Workout, Long> {

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
     * Finds a Workout model by their date and associated User
     *
     * @param user User to find the workout for
     * @param date The date to use to find a Workout
     * @return The found Workout or null
     */
    public Workout findByUserAndDate(User user, final String date ) {
        return workoutRepository.findByUserAndDate(user, date);
    }

    /**
     * Finds the workouts for a user
     * @param user The user to find the workouts for
     * @return The list of workouts for the user
     */
    public List<Workout> findByUser(User user) {
        return workoutRepository.findByUser(user);
    }
}
