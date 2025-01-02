package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.repositories.ExerciseRepository;
import com.hpatel.Tempest_Fitness.repositories.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WeightService extends Service<Weight, Long> {

    /** The instance of the repository */
    @Autowired
    private WeightRepository weightRepository;

    @Override
    protected JpaRepository<Weight, Long> getRepository() {
        return weightRepository;
    }

    public Weight findByDate ( final String date ) {
        return weightRepository.findByDate( date );
    }
}
