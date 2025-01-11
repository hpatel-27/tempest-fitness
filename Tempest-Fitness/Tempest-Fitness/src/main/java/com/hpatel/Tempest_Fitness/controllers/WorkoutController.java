package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.services.WorkoutService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller that holds the REST endpoints that handle getting,
 * creating, updating, and deleting Workouts
 * Spring will automatically convert all the ResponseEntity and List results
 * to JSON
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class WorkoutController extends APIController {

    /**
     * WorkoutService object, to be autowired in by Spring to allow for
     * manipulating the Workout model
     */
    @Autowired
    private WorkoutService service;

    /**
     * REST API method to provide GET access to all workouts in the system
     *
     * @return JSON representation of all workouts
     */
    @GetMapping( BASE_PATH + "/workouts" )
    public List<Workout> getWorkouts() {
        return service.findAll();
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
    public ResponseEntity getWorkout (@PathVariable ( "date" ) final String date ) {
        final Workout workout = service.findByDate( date );
        return null == workout
                ? new ResponseEntity( errorResponse( "No workout found with date " + date ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( workout, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Workout model. This is used
     * to create a new Workout by automatically converting the JSON RequestBody
     * provided to a Workout object. Invalid JSON will fail.
     *
     * @param workout
     *            The valid Workout to be saved.
     * @return ResponseEntity indicating success if the Workout could be saved to
     *         the database, or an error if it could not be
     */
    @PostMapping( BASE_PATH + "/workouts" )
    public ResponseEntity createWorkout ( @RequestBody final Workout workout ) {
        if ( null != service.findByDate( workout.getDate() ) ) {
            return new ResponseEntity( errorResponse( "Workout with the name " + workout.getDate() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        else {
            service.save( workout );
            return new ResponseEntity( successResponse( "Workout on " + workout.getDate() + " successfully created" ), HttpStatus.OK );
        }

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
    public ResponseEntity deleteWorkout ( @PathVariable final String date ) {
        final Workout workout = service.findByDate( date );
        if ( null == workout ) {
            return new ResponseEntity( errorResponse( "No workout found for name " + date ), HttpStatus.NOT_FOUND );
        }
        service.delete( workout );

        return new ResponseEntity( successResponse( "Workout on " + date + " was deleted successfully" ), HttpStatus.OK );
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
    public ResponseEntity editWorkout (@PathVariable final String date, @RequestBody final Workout newWorkout ) {
        // Get the current workout matching date
        final Workout currWorkout = service.findByDate( date );

        // Return not found if the workout with the given date doesn't exist
        if ( null == currWorkout ) {
            return new ResponseEntity( errorResponse( "No workout found for date " + date ), HttpStatus.NOT_FOUND );
        }

        // Update the current workout to match the new one
        currWorkout.updateWorkout( newWorkout );
        service.save( currWorkout );

        // Return a success response
        return new ResponseEntity( successResponse( "Workout on " + currWorkout.getDate() + " successfully updated" ), HttpStatus.OK );
    }
}
