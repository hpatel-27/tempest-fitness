package com.hpatel.Tempest_Fitness.config;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Initializes Admin and Guest user's in the system. */
@Configuration
public class UserInitializer {

    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Password encoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initializes the Admin user
     */
    @PostConstruct
    public void initializeAdminUser () {
        if ( userRepository.findByUsername( "harshpatel" ) == null ) {
            final User adminUser = new User();
            adminUser.setUsername( "harshpatel" );
            adminUser.setPassword( passwordEncoder.encode( "goodpassword" ) );
            adminUser.setRole( "ADMIN" );
            userRepository.save( adminUser );
        }
    }

}
