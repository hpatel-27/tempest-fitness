package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class UserController extends APIController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * REST API method to provide GET access to all users in the system
     *
     * @return JSON representation of all users
     */
    @GetMapping ( BASE_PATH + "/users")
    public List<User> getUsers() {
        return service.findAll();
    }

//    /**
//     * Get the current user from Spring security
//     *
//     * @return Currently authenticated UserDetails object
//     */
//    @GetMapping ( BASE_PATH + "/currentuser" )
//    public ResponseEntity getCurrentUser () {
//        final UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return null == user ? new ResponseEntity( errorResponse( "Error finding current user" ), HttpStatus.NOT_FOUND )
//                : new ResponseEntity( user, HttpStatus.OK );
//    }

    /**
     * REST API method to provide GET access to a specific user, as indicated by
     * the path variable provided (the name of the user desired)
     *
     * @param username
     *            The name of the User to get
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/users/{username}" )
    public ResponseEntity getUserByUsername ( @PathVariable final String username ) {
        final User user = service.findByName( username );
        return null == user
                ? new ResponseEntity( errorResponse( "No user found with name " + username ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( user, HttpStatus.OK );
    }

//    /**
//     * REST API method to provide POST access to the User model. This is used to
//     * create a new User by automatically converting the JSON RequestBody
//     * provided to a User object. Invalid JSON will fail.
//     *
//     * @param user
//     *            The valid User to be saved.
//     * @return ResponseEntity indicating success if the User could be saved to
//     *         the db, or an error if it could not be
//     */
//    @PostMapping( BASE_PATH + "/users" )
//    public ResponseEntity createUser ( @RequestBody final User user ) {
//        // make sure the users does not already exist in some capacity
//        if ( null != service.findByName( user.getUsername() ) ) {
//            return new ResponseEntity( errorResponse( "Username already in use" ), HttpStatus.CONFLICT );
//        }
//        else {
//            final User newUser = new User();
//            newUser.setUsername( user.getUsername() );
//            newUser.setPassword( passwordEncoder.encode( user.getPassword() ) );
//            newUser.setRole( user.getRole() );
//
//
//            service.save( newUser );
//
//            return new ResponseEntity( user, HttpStatus.OK );
//        }
//    }

    /**
     * REST API method to provide delete access to a User model. This is used to
     * delete a User already in the database.
     *
     * @param username
     *            The name of the User to delete
     * @return ResponseEntity indicating success if the user was successfully
     *         deleted or an error if it could not be
     *
     */
    @DeleteMapping( BASE_PATH + "/users/{username}" )
    public ResponseEntity deleteUser ( @PathVariable final String username ) {
        final User user = service.findByName( username );
        if ( null == user ) {
            return new ResponseEntity( errorResponse( "No user found with the name " + username ),
                    HttpStatus.NOT_FOUND );
        }
        service.delete( user );

        return new ResponseEntity( successResponse( user.getUsername() + " was successfully deleted!" ),
                HttpStatus.OK );
    }
}
