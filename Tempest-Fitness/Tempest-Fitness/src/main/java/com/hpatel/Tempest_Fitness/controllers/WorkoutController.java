package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.UserExercise;
import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.services.UserService;
import com.hpatel.Tempest_Fitness.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller that holds the REST endpoints that handle getting,
 * creating, updating, and deleting Workouts
 * Spring will automatically convert all the ResponseEntity and List results
 * to JSON
 */
@RestController
public class WorkoutController extends APIController {

    /**
     * WorkoutService object, to be autowired in by Spring to allow for
     * manipulating the Workout model
     */
    @Autowired
    private WorkoutService service;

    @Autowired
    private UserService userService;

    /**
     * REST API method to provide GET access to all workouts for a user
     *
     * @return JSON representation of all workouts
     */
    @GetMapping( BASE_PATH + "/workouts" )
    public ResponseEntity<?> getWorkouts() {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }
        List<Workout> workouts = service.findByUser(user);
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    /**
     * REST API method to provide GET access to a specific workout, as indicated
     * by the path variable provided (the date of the workout desired)
     *
     * @param date
     *            workout date
     * @return response to the request
     */
    @GetMapping( BASE_PATH + "/workouts/{date}")
    public ResponseEntity<?> getWorkout (@PathVariable ( "date" ) final String date ) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }
        final Workout workout = service.findByUserAndDate(user, date);
        return null == workout
                ? new ResponseEntity<>( errorResponse( "No workout found with date " + date ), HttpStatus.NOT_FOUND )
                : new ResponseEntity<>( workout, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Workout model. This is used
     * to create a new Workout by automatically converting the JSON RequestBody
     * provided to a Workout object. Invalid JSON will fail.
     *
     * @param workoutRequest
     *            The valid Workout to be saved.
     * @return ResponseEntity indicating success if the Workout could be saved to
     *         the database, or an error if it could not be
     */
    @PostMapping( BASE_PATH + "/workouts" )
    public ResponseEntity<?> createWorkout ( @RequestBody final Workout workoutRequest ) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }
        if ( null != service.findByUserAndDate(user, workoutRequest.getDate() ) ) {
            return new ResponseEntity<>( errorResponse( "Workout with the name " + workoutRequest.getDate() + " already exists" ),
                    HttpStatus.CONFLICT );
        }

        // Create workout instance
        Workout saveWorkout = new Workout(workoutRequest.getDate(), user);

        // Link each UserExercise to this workout
        for (UserExercise ue : workoutRequest.getUserExercises()) {
            ue.setWorkout(saveWorkout);
            saveWorkout.addOrUpdateExercise(ue);
        }

        service.save(saveWorkout);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Workout created successfully for date: " + saveWorkout.getDate());

    }

    /**
     * REST API method to allow deleting a Workout,
     * by making a DELETE request to the API endpoint and indicating
     * the workout to delete (as a path variable)
     *
     * @param date
     *            The date of the Workout to delete
     * @return Success if the Workout could be deleted; an error if the Workout
     *         does not exist
     */
    @DeleteMapping ( BASE_PATH + "/workouts/{date}" )
    public ResponseEntity<?> deleteWorkout ( @PathVariable final String date ) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }
        final Workout workout = service.findByUserAndDate(user, date);
        if ( null == workout ) {
            return new ResponseEntity<>( errorResponse( "No workout found for name " + date ), HttpStatus.NOT_FOUND );
        }
        service.delete( workout );

        return new ResponseEntity<>( successResponse( "Workout on " + date + " was deleted successfully" ), HttpStatus.OK );
    }

    /**
     * REST API method to provide PUT access to the Workout model. This is used
     * to edit and update a Workout already in the database.
     *
     * @param date
     *            The name of the Workout to update
     * @param newWorkout
     *            Workout matching the updated Workout
     * @return ResponseEntity indicating success if the Workout was successfully
     *         updated or an error if it could not be
     *
     */
    @PutMapping ( BASE_PATH + "/workouts/{date}" )
    public ResponseEntity<?> editWorkout (@PathVariable final String date, @RequestBody final Workout newWorkout ) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        // Get the current workout matching date
        final Workout currWorkout = service.findByUserAndDate(user, date);

        // Return not found if the workout with the given date doesn't exist
        if ( null == currWorkout ) {
            return new ResponseEntity<>( errorResponse( "No workout found for date " + date ), HttpStatus.NOT_FOUND );
        }

        // Update the current workout to match the new one
        currWorkout.updateWorkout( newWorkout );
        service.save( currWorkout );

        // Return a success response
        return new ResponseEntity<>( successResponse( "Workout on " + currWorkout.getDate() + " successfully updated" ), HttpStatus.OK );
    }

    /**
     * Helper method to get the authenticated user, since you must be authenticated to make
     * api requests
     * @return currently authenticated user
     */
    private User getAuth() {
        // Get authenticated user's username
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Find the user by username and send it back
        return userService.findByName(username);
    }
}
