package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.models.Exercise;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.UserExercise;
import com.hpatel.Tempest_Fitness.models.Workout;
import com.hpatel.Tempest_Fitness.services.ExerciseService;
import com.hpatel.Tempest_Fitness.services.UserExerciseService;
import com.hpatel.Tempest_Fitness.services.UserService;
import com.hpatel.Tempest_Fitness.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hpatel.Tempest_Fitness.controllers.APIController.*;

@RestController
public class UserExerciseController {

    @Autowired
    private UserExerciseService userExerciseService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserService userService;

    /**
     * Get all the Exercises for a workout
     * @param id The id of the workout we want the exercises for
     * @return ResponseEntity with the UserExercises in the Workout
     */
    @GetMapping(BASE_PATH + "/user-exercises/workout/{id}")
    public ResponseEntity<?> getExercisesForWorkout(@PathVariable Long id) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        Workout workout = workoutService.findById(id);
        if (workout ==  null) {
            return new ResponseEntity<>(errorResponse("Workout not found."), HttpStatus.NOT_FOUND);
        }

        List<UserExercise> exercises = userExerciseService.findByWorkout(workout);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    /**
     * Add a UserExercise to the Workout
     * @param id The id of the workout
     * @param userExercise The UserExercise to add
     * @return ResponseEntity indicating success if the UserExercise could be
     *          saved to the database, or an error if it could not be
     */
    @PostMapping(BASE_PATH + "/user-exercises/workout/{id}")
    public ResponseEntity<?> addUserExercise(@PathVariable Long id, @RequestBody UserExercise userExercise) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        Workout workout = workoutService.findById(id);
        if (workout == null) {
            return new ResponseEntity<>(errorResponse("Workout was not found."), HttpStatus.NOT_FOUND);
        }
        else if (!workout.getUser().getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>(errorResponse("Workout does not belong to this user."), HttpStatus.UNAUTHORIZED);
        }

        Exercise exercise = exerciseService.findById(userExercise.getExercise().getId());
        if (exercise == null) {
            return new ResponseEntity<>(errorResponse("Exercise not found."), HttpStatus.NOT_FOUND);
        }

        userExercise.setWorkout(workout);
        userExercise.setExercise(exercise);
        userExerciseService.save(userExercise);
        return new ResponseEntity<>(successResponse("UserExercise successfully created."), HttpStatus.CREATED);
    }

    @PutMapping(BASE_PATH + "/user-exercises")
    public ResponseEntity<?> updateUserExercise(@PathVariable Long id, @RequestBody UserExercise updatedExercise) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        UserExercise existing = userExerciseService.findById(id);
        if (existing == null) {
            return new ResponseEntity<>(errorResponse("UserExercise not found."), HttpStatus.NOT_FOUND);
        }
        else if (!existing.getWorkout().getUser().getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>(errorResponse("UserExercise not owned by this user."), HttpStatus.UNAUTHORIZED);
        }

        // Only update performance information
        existing.updatePerformance(updatedExercise.getSets(), updatedExercise.getReps(), updatedExercise.getWeight());
        return new ResponseEntity<>(successResponse("UserExercise updated successfully!"), HttpStatus.OK);
    }

    @DeleteMapping(BASE_PATH + "/user-exercises")
    public ResponseEntity<?> deleteUserExercise(@PathVariable Long id) {
        User user = getAuth();
        if (user == null) {
            return new ResponseEntity<>(errorResponse("User not found."), HttpStatus.NOT_FOUND);
        }

        UserExercise userExercise = userExerciseService.findById(id);
        if (userExercise == null) {
            return new ResponseEntity<>(errorResponse("UserExercise not found."), HttpStatus.NOT_FOUND);
        }
        else if (!userExercise.getWorkout().getUser().getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>(errorResponse("UserExercise not owned by this user."), HttpStatus.UNAUTHORIZED);
        }

        userExerciseService.delete(userExercise);
        return new ResponseEntity<>(successResponse("UserExercise deleted successfully!"), HttpStatus.OK);
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
