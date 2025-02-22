package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository is used to find the specific Weight models from the database.
 * Spring will generate appropriate code with JPA.
 */
public interface WeightRepository extends JpaRepository<Weight, Long> {
    /**
     * Finds a Weight object with the provided date.
     *
     * @param user User for the weight
     * @param date date of the weigh-in
     * @return Found weight, null if none.
     */
    Weight findByUserAndDate (User user, String date);

    /**
     * Finds all Weight objects by a User.
     *
     * @param user User to query for
     * @return Found weights list, null if none.
     */
    List<Weight> findByUser(User user);
}
