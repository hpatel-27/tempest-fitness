package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.services.UserService;
import com.hpatel.Tempest_Fitness.services.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller that holds the REST endpoints that handle getting,
 * creating, updating, and deleting Weights.
 * Spring will automatically convert all the ResponseEntity and List results
 * to JSON
 */
@RestController
public class WeightController extends APIController {

    /**
     * WeightService object, to be autowired in by Spring to allow for
     * manipulating the Weight model
     */
    @Autowired
    private WeightService weightService;

    @Autowired
    private UserService userService;
    /**
     * REST API method to provide GET access to the Weight model.
     * This is used to get all the Weights by automatically converting the
     * JSON RequestBody provided to a Weight object. Invalid JSON will fail.
     * @return List of Weights for the user
     */
    @GetMapping( BASE_PATH + "/weights")
    public ResponseEntity<?> getUserWeights() {
        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Find the user by username
        User user = userService.findByName(username);
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        // Fetch all weights for this user
        List<Weight> weights = weightService.findByUser(user);
        return new ResponseEntity<>(weights, HttpStatus.OK);
    }


    /**
     * REST API method to provide POST access to the Weight model. This is
     * used to create a new Weight by automatically converting the JSON
     * RequestBody provided to a Weight object. Invalid JSON will fail.
     *
     * @param weight
     *            The valid Weight to be saved.
     * @return ResponseEntity indicating success if the Weight could be
     *         saved to the database, or an error if it could not be
     */
    @PostMapping( BASE_PATH + "/weights" )
    public ResponseEntity<?> addWeight (@RequestBody final Weight weight ) {
        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByName(username);
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        if (weightService.findByUserAndDate(user, weight.getDate()) != null ) {
            return new ResponseEntity<>(errorResponse("Weight with the date " + weight.getDate() + " already exists."), HttpStatus.CONFLICT);
        }
        weight.setUser(user);
        weightService.save( weight );
        return new ResponseEntity<>(successResponse("Weight on " + weight.getDate() + " successfully created."), HttpStatus.OK);
    }

    /**
     * REST API method to provide PUT access to the Weight model. This is
     * used to edit an existing Weight by automatically converting the JSON
     * RequestBody provided to a Weight object. Invalid JSON will fail.
     *
     * @param date
     *            the date of the weight
     * @param weight
     *            the weight to be saved
     * @return ResponseEntity indicating success if the Weight could be
     *         saved to the database, or an error if it could not be
     */
    @PutMapping ( BASE_PATH + "/weights/{date}" )
    public ResponseEntity<?> editWeight ( @PathVariable ( "date" ) final String date,
                                           @RequestBody final Weight weight ) {

        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userService.findByName(username);
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        final Weight existingWeight = weightService.findByUserAndDate(user, date );
        if ( existingWeight == null ) {
            return new ResponseEntity<>(errorResponse("Weight with the date " + weight.getDate() + " does not exist."), HttpStatus.CONFLICT);
        }
        if ( !date.equals( weight.getDate() ) ) {
            return new ResponseEntity<>(errorResponse("Weight with the date " + date + " does not match object provided."), HttpStatus.CONFLICT);
        }
        existingWeight.setDate( weight.getDate() );
        existingWeight.setWeight( weight.getWeight() );
        weightService.save( existingWeight );
        return new ResponseEntity<>( successResponse( weight.getDate() + " successfully created" ), HttpStatus.OK );
    }

    /**
     * REST API method to allow deleting a Weight from the User's
     * calendar, by making a DELETE request to the API endpoint and indicating
     * the weight to delete (as a path variable)
     *
     * @param date
     *            The date of the Weight to delete
     * @return Success if the weight could be deleted; an error if the
     *         weight does not exist
     */
    @DeleteMapping ( BASE_PATH + "/weights/{date}" )
    public ResponseEntity<?> deleteWeight ( @PathVariable final String date ) {
        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userService.findByName(username);
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        final Weight weight = weightService.findByUserAndDate(user, date );
        if ( null == weight ) {
            return new ResponseEntity<>(errorResponse("No Weight found for date " + date), HttpStatus.NOT_FOUND);
        }
        weightService.delete( weight );
        return new ResponseEntity<>(successResponse("Weight with date " + date + " was deleted successfully!"), HttpStatus.OK);
    }

}
