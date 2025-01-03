package com.hpatel.Tempest_Fitness.repositories;

import com.hpatel.Tempest_Fitness.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User object by their username
     * @param username Username of a user
     * @return the User object associated with the username
     */
    User findByUsername (String username);
}
