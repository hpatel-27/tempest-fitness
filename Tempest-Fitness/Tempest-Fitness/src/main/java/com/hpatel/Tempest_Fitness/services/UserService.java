package com.hpatel.Tempest_Fitness.services;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserService extends Service<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public User findByName ( final String username ) {
        return userRepository.findByName( username );
    }
}
