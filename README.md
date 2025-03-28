# Tempest-Fitness

Fitness tracking application to store and display user weight information, which can be sorted in ascending or descending order. The user must login to access their information. The exercise and workout components are a work in progress. 

# Application Preview
This will take you to the youtube video that showcases the demo of this application.

[![Demo of Tempest-Fitness](Tempest-Fitness/docs/LoginPage.png)](https://youtu.be/TCfRo5PG9Xg)

# Directory Structure:
```
|-- Tempest-Fitness
|   |-- Tempest-Fitness
|   |   |-- mvnw
|   |   |-- mvnw.cmd
|   |   |-- pom.xml
|   |   `-- src
|   |       |-- main
|   |       |   |-- java
|   |       |   |   `-- com
|   |       |   |       `-- hpatel
|   |       |   |           `-- Tempest_Fitness
|   |       |   |               |-- TempestFitnessApplication.java
|   |       |   |               |-- config
|   |       |   |               |   |-- CustomUserDetailsService.java
|   |       |   |               |   |-- SecurityConfig.java
|   |       |   |               |   `-- UserInitializer.java
|   |       |   |               |-- controllers
|   |       |   |               |   |-- APIController.java
|   |       |   |               |   |-- AuthController.java
|   |       |   |               |   |-- ExerciseController.java
|   |       |   |               |   |-- UserController.java
|   |       |   |               |   |-- UserExerciseController.java
|   |       |   |               |   |-- WeightController.java
|   |       |   |               |   `-- WorkoutController.java
|   |       |   |               |-- dto
|   |       |   |               |   `-- LoginRequest.java
|   |       |   |               |-- models
|   |       |   |               |   |-- DomainObject.java
|   |       |   |               |   |-- Exercise.java
|   |       |   |               |   |-- User.java
|   |       |   |               |   |-- UserExercise.java
|   |       |   |               |   |-- Weight.java
|   |       |   |               |   `-- Workout.java
|   |       |   |               |-- repositories
|   |       |   |               |   |-- ExerciseRepository.java
|   |       |   |               |   |-- UserExerciseRepository.java
|   |       |   |               |   |-- UserRepository.java
|   |       |   |               |   |-- WeightRepository.java
|   |       |   |               |   `-- WorkoutRepository.java
|   |       |   |               |-- services
|   |       |   |               |   |-- CustomService.java
|   |       |   |               |   |-- ExerciseService.java
|   |       |   |               |   |-- UserExerciseService.java
|   |       |   |               |   |-- UserService.java
|   |       |   |               |   |-- WeightService.java
|   |       |   |               |   `-- WorkoutService.java
|   |       |   |               `-- utility
|   |       |   `-- resources
|   |       |       |-- application.properties
|   |       |       |-- keystore.p12
|   |       |       |-- static
|   |       |       `-- templates
|   |       `-- test
|   |           |-- java
|   |           |   `-- com
|   |           |       `-- hpatel
|   |           |           `-- Tempest_Fitness
|   |           |               |-- DBInteractionTest.java
|   |           |               |-- TempestFitnessApplicationTests.java
|   |           |               |-- TestConfig.java
|   |           |               |-- common
|   |           |               |   `-- TestUtils.java
|   |           |               |-- controllers
|   |           |               |   |-- APIExerciseTest.java
|   |           |               |   |-- APIUserTest.java
|   |           |               |   |-- APIWeightTest.java
|   |           |               |   `-- APIWorkoutTest.java
|   |           |               `-- models
|   |           |                   |-- ExerciseTest.java
|   |           |                   |-- UserExerciseTest.java
|   |           |                   |-- UserTest.java
|   |           |                   |-- WeightTest.java
|   |           |                   `-- WorkoutTest.java
|   |           `-- resources
|   |               `-- application-test.properties
|   |-- docs
|   |   |-- Demo.mp4
|   |   |-- LoginPage.png
|   |   `-- RegisterPage.png
|   `-- tempest-frontend
|       |-- README.md
|       |-- cert.pem
|       |-- eslint.config.js
|       |-- index.html
|       |-- key.pem
|       |-- package-lock.json
|       |-- package.json
|       |-- public
|       |   `-- thunder.svg
|       |-- src
|       |   |-- App.jsx
|       |   |-- assets
|       |   |   `-- storm_weather.png
|       |   |-- components
|       |   |   |-- Exercises.jsx
|       |   |   |-- Home.jsx
|       |   |   |-- Login.jsx
|       |   |   |-- NavBar.jsx
|       |   |   |-- Profile.jsx
|       |   |   |-- ProtectedRoute.jsx
|       |   |   |-- Register.jsx
|       |   |   |-- Weights.jsx
|       |   |   `-- Workouts.jsx
|       |   |-- contexts
|       |   |   `-- AuthContext.jsx
|       |   |-- main.jsx
|       |   |-- providers
|       |   |   `-- AuthProvider.jsx
|       |   |-- services
|       |   |   |-- exerciseService.js
|       |   |   |-- userService.js
|       |   |   |-- weightService.js
|       |   |   `-- workoutService.js
|       |   `-- styles
|       |       |-- exercise.css
|       |       |-- home.css
|       |       |-- index.css
|       |       |-- login.css
|       |       |-- navbar.css
|       |       |-- register.css
|       |       `-- weight.css
|       `-- vite.config.js

37 directories, 87 files
```

