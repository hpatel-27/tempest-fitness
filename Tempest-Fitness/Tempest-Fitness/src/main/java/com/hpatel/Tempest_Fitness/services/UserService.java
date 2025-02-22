package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to the repository and getting User models
 * from the database.
 */
@Component
@Transactional
public class UserService extends Service<User, Long> {

    /** Instance of the repository */
    @Autowired
    private UserRepository userRepository;

    /**
     * Provides access to the repository
     * @return The UserRepository
     */
    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    /**
     * Finds a User model by their username
     * @param username The username to use to find a User
     * @return The found User or null
     */
    public User findByName ( final String username ) {

        return userRepository.findByUsername( username );
    }
}
