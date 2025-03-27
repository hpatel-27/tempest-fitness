package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository is used to find the specific User models from the database.
 * Spring will generate appropriate code with JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User object by their username
     * @param username Username of a user
     * @return the User object associated with the username
     */
    User findByUsername (String username);
}
