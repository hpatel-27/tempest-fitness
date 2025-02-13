package com.hpatel.Tempest_Fitness.config;


import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/***
 * Service handling custom UserDetails class.Helps retrieve UserDetails
 * objects*from a given username.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Gets a user from the repository based on their username
     *
     * @param username
     *            Username of the user to load
     *
     * @return User with the matching username
     *
     * @throws UsernameNotFoundException
     *             if user isn't found
     */
    @Override
    public UserDetails loadUserByUsername ( final String username ) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername( username );
        if ( user == null ) {
            throw new UsernameNotFoundException( "User not found with username: " + username );
        }
        return user;
    }
}
