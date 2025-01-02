package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.services.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller that holds the REST endpoints that handle getting,
 * creating, updating, and deleting Weights.
 * Spring will automatically convert all  the ResponseEntity and List results
 * to JSON
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class WeightController extends APIController {

    /**
     * WeightService object, to be autowired in by Spring to allow for
     * manipulating the Weight model
     */
    @Autowired
    private WeightService service;

    /**
     * REST API method to provide GET access to the Weight model.
     * This is used to get all the Weights by automatically converting the
     * JSON RequestBody provided to a Weight object. Invalid JSON will fail.
     * @return List of Weights for the user
     */
    @GetMapping( BASE_PATH + "/weights")
    public List<Weight> getWeights() {
        return service.findAll();
    }

    @GetMapping( BASE_PATH + "/weights/{date}" )
    public ResponseEntity getWeight( @PathVariable("date") final String date ) {
        final Weight w = service.findByDate( date );

        return null == w
                ? new ResponseEntity( errorResponse( "No weigh-in found with date" + date ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( w, HttpStatus.OK );
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
    public ResponseEntity addWeight ( @RequestBody final Weight weight ) {
        if ( null != service.findByDate( weight.getDate() ) ) {
            return new ResponseEntity(
                    errorResponse( "Weight with the date " + weight.getDate() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        service.save( weight );
        return new ResponseEntity( successResponse( weight.getDate() + " successfully created" ),
                HttpStatus.OK );

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
    public ResponseEntity editWeight ( @PathVariable ( "date" ) final String date,
                                           @RequestBody final Weight weight ) {

        if ( null == service.findByDate( date ) ) {
            return new ResponseEntity(
                    errorResponse(
                            "Weight with the date " + weight.getDate() + " does not exist" ),
                    HttpStatus.CONFLICT );
        }
        if ( !date.equals( weight.getDate() ) ) {
            return new ResponseEntity(
                    errorResponse( "Weight with the date " + date + " does not match object provided" ),
                    HttpStatus.CONFLICT );
        }
        final Weight w = service.findByDate( date );
        w.setDate( weight.getDate() );
        service.save( w );
        return new ResponseEntity( successResponse( weight.getDate() + " successfully created" ),
                HttpStatus.OK );

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
    public ResponseEntity deleteWeight ( @PathVariable final String date ) {

        final Weight weight = service.findByDate( date );
        if ( null == weight ) {
            return new ResponseEntity( errorResponse( "No Weight found for date " + date ), HttpStatus.NOT_FOUND );
        }
        service.delete( weight );

        return new ResponseEntity( successResponse( "Weight with date" + date + " was deleted successfully" ), HttpStatus.OK );
    }

}
