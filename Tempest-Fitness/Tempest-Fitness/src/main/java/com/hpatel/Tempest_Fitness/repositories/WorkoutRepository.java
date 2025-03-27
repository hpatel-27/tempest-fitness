package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository is used to find the specific Workout models from the database.
 * Spring will generate appropriate code with JPA.
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    /**
     * Finds a Workout object with the provided date.
     *
     * @param user User for the workout
     * @param date date of the workout
     * @return Found workout, null if none.
     */
    Workout findByUserAndDate (User user, String date);

    /**
     * Finds all the Workouts by a User.
     * @param user User to query for
     * @return Found workouts list, null if none
     */
    List<Workout> findByUser(User user);
}
