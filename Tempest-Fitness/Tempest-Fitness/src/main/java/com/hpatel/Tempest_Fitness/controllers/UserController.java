package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class UserController extends APIController {

    @Autowired
    private UserService service;

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

        final User checkUser = service.findByName(username);

        // If user is not null, return a success, otherwise a not found
        return null == checkUser ? new ResponseEntity( successResponse( username + " was successfully deleted!" ),
                HttpStatus.OK ) : new ResponseEntity( errorResponse( "Error deleting user: " + username ), HttpStatus.BAD_REQUEST );
    }

    public ResponseEntity editUser(@PathVariable final String username, @RequestBody User user) {
        return null;
    }
}
