package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.Exercise;
import com.hpatel.Tempest_Fitness.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller that holds the REST endpoints that handle getting,
 * creating, updating, and deleting Exercises.
 * Spring will automatically convert all the ResponseEntity and List results
 * to JSON
 */
@RestController
public class ExerciseController extends APIController {

    /**
     * ExerciseService object, to be autowired in by Spring to allow for
     * manipulating the Exercise model
     */
    @Autowired
    private ExerciseService service;

    /**
     * REST API method to provide GET access to all Exercises in the system
     *
     * @return JSON representation of all exercises
     */
    @GetMapping( BASE_PATH + "/exercises")
    public List<Exercise> getExercises() {
        return service.findAll();
    }

    /**
     * REST API method to provide GET access to a specific Exercise
     *
     * @param id
     *            id of the exercise to get
     * @return JSON representation of the specific Exercise
     */
    @GetMapping( BASE_PATH + "/exercises/{id}")
    public ResponseEntity<?> getExercise( @PathVariable("id") Long id ) {

        final Exercise exercise = service.findById(id);

        return null == exercise
                ? new ResponseEntity<>( errorResponse( "No exercise found." ), HttpStatus.NOT_FOUND )
                : new ResponseEntity<>( exercise, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Exercise model. This is
     * used to create a new Exercise by automatically converting the JSON
     * RequestBody provided to an Exercise object. Invalid JSON will fail.
     *
     * @param exercise
     *            The valid Exercise to be saved.
     * @return ResponseEntity indicating success if the Exercise could be
     *         saved to the database, or an error if it could not be
     */
    @PostMapping( BASE_PATH + "/exercises" )
    public ResponseEntity<?> addExercise ( @RequestBody final Exercise exercise) {
        if ( null != service.findByName( exercise.getName() ) ) {
            return new ResponseEntity<>(
                    errorResponse( "Exercise with the name " + exercise.getName() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        service.save(exercise);
        return new ResponseEntity<>( successResponse( exercise.getName() + " successfully created" ),
                HttpStatus.OK );

    }

    /**
     * REST API method to provide PUT access to the Exercise model. This is
     * used to edit an existing Exercise by automatically converting the JSON
     * RequestBody provided to an Exercise object. Invalid JSON will fail.
     *
     * @param id
     *            the id of the exercise
     * @param updatedExercise
     *            the exercise to be saved
     * @return ResponseEntity indicating success if the Exercise could be
     *         updated, or an error if it could not be
     */
    @PutMapping ( BASE_PATH + "/exercises/{id}" )
    public ResponseEntity<?> editExercise ( @PathVariable("id") Long id,
                                       @RequestBody final Exercise updatedExercise) {

        Exercise existing = service.findById(id);
        if ( existing == null ) {
            return new ResponseEntity<>(
                    errorResponse("Exercise does not exist." ), HttpStatus.CONFLICT );
        }

        existing.setName(updatedExercise.getName());
        existing.setType(updatedExercise.getType());
        existing.setMuscle(updatedExercise.getMuscle());
        existing.setEquipment(updatedExercise.getEquipment());
        existing.setDifficulty(updatedExercise.getDifficulty());
        service.save( existing );
        return new ResponseEntity<>( successResponse( existing.getName() + " successfully updated!" ),
                HttpStatus.OK );

    }

    /**
     * REST API method to allow deleting an Exercise from the system,
     * by making a DELETE request to the API endpoint and indicating
     * the exercise to delete (as a path variable)
     *
     * @param id
     *            The id of the Exercise to delete
     * @return Success if the Exercise could be deleted; an error if the
     *         Exercise does not exist
     */
    @DeleteMapping ( BASE_PATH + "/exercises/{id}" )
    public ResponseEntity<?> deleteExercise ( @PathVariable("id") final Long id ) {

        final Exercise exercise = service.findById(id);
        if ( null == exercise) {
            return new ResponseEntity<>( errorResponse( "No Exercise found."), HttpStatus.NOT_FOUND );
        }
        service.delete(exercise);
        return new ResponseEntity<>( successResponse( "Exercise was deleted successfully" ), HttpStatus.OK );
    }

}
