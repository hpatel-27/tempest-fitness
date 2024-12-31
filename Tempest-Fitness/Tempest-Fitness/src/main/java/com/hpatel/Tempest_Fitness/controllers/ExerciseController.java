package com.hpatel.Tempest_Fitness.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {
    @GetMapping("/api/exercises")
    public String getExercises() {
        return "This is an exercise. Look at it in awe and give me the Infinity Stones.";
    }
}
