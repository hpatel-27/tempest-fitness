package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends APIController {

    @Autowired
    private UserService service;

    /**
     * Get the current user from Spring security
     *
     * @return Currently authenticated User object
     */
    @GetMapping( BASE_PATH + "/currentUser" )
    public ResponseEntity<?> getCurrentUser() {
        try {
            final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NullPointerException e ) {
            return new ResponseEntity<>(errorResponse("Error finding current user."), HttpStatus.NOT_FOUND);
        }
    }

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
    public ResponseEntity<String> deleteUser( @PathVariable final String username ) {
        final User user = service.findByName( username );
        if ( null == user ) {
            return new ResponseEntity<>(errorResponse("No user found with the name " + username + "."), HttpStatus.NOT_FOUND);
        }
        service.delete( user );

        // Return indicating that the user was deleted
        return new ResponseEntity<>( successResponse( user.getUsername() + " was successfully deleted!" ),
                HttpStatus.OK );
    }

//    /**
//     * Edits a User's information (username, password, name, height)
//     *
//     * @param username The username of the user currently
//     * @param user The new User object with the updated info
//     * @return ResponseEntity indicating outcome of the operation
//     */
//    public ResponseEntity editUser(@PathVariable final String username, @RequestBody User user) {
//        // TODO: Allow users to change their info
//        return null;
//    }
}
