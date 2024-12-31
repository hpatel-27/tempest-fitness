package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    /**
     * Finds a Workout object with the provided date.
     *
     * @param date date of the workout
     * @return Found workout, null if none.
     */
    Workout findByDate (String date);
}
