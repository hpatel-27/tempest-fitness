package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.repositories.WeightRepository;
import com.hpatel.Tempest_Fitness.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class WorkoutService extends Service<Workout, Long> {

    /** The instance of the repository */
    @Autowired
    private WorkoutRepository workoutRepository;


    @Override
    protected JpaRepository<Workout, Long> getRepository() {
        return workoutRepository;
    }

    public Workout findByDate (final String date ) {
        return workoutRepository.findByDate( date );
    }
}
