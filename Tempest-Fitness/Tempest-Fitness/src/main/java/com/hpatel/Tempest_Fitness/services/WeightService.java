package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.repositories.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to the repository and getting Weight models
 * from the database.
 */
@Component
@Transactional
public class WeightService extends Service<Weight, Long> {

    /** The instance of the repository */
    @Autowired
    private WeightRepository weightRepository;

    /**
     * Provides access to the repository
     * @return The UserRepository
     */
    @Override
    protected JpaRepository<Weight, Long> getRepository() {
        return weightRepository;
    }

    /**
     * Finds a Weight model by their date
     * @param date The date to use to find a Weight
     * @return The found Weight or null
     */
    public Weight findByDate ( final String date ) {
        return weightRepository.findByDate( date );
    }
}
