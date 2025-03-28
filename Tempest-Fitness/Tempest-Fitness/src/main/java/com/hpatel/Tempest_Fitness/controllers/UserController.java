package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(errorResponse("Error finding the current user."), HttpStatus.NOT_FOUND);
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
    public ResponseEntity<String> deleteUser( @PathVariable("username") final String username ) {
        final User user = service.findByName( username );
        if ( user == null ) {
            return new ResponseEntity<>(errorResponse("No user found with the name " + username + "."), HttpStatus.NOT_FOUND);
        }
        service.delete( user );

        // Return indicating that the user was deleted
        return new ResponseEntity<>(successResponse(user.getUsername() + " was successfully deleted!"), HttpStatus.OK);
    }

    /**
     * Edits a User's information (username, role, first name, last name, height)
     *
     * @param username The username of the user currently
     * @param updatedUser The new User object with the updated info
     * @return ResponseEntity indicating outcome of the operation
     */
    @PutMapping(BASE_PATH + "/users/{username}")
    public ResponseEntity<?> editUser(@PathVariable("username") final String username, @RequestBody User updatedUser) {

        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUsername;

        if (principal instanceof User) {
            authUsername = ((User) principal).getUsername();
        } else {
            authUsername = principal.toString();
        }

        if (!authUsername.equals(username)) {
            return new ResponseEntity<>(errorResponse("Username's do not match."), HttpStatus.UNAUTHORIZED);
        }
        if (updatedUser.getHeight() < 0 ) {
            return new ResponseEntity<>(errorResponse("Height must not be negative."), HttpStatus.BAD_REQUEST);
        }
        // Find the user by username
        User existingUser = service.findByName(authUsername);
        if (existingUser == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }
        existingUser.setRole(updatedUser.getRole());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setHeight(updatedUser.getHeight());

        service.save(existingUser);

        return new ResponseEntity<>(successResponse("User successfully updated!"), HttpStatus.OK);
    }
}
