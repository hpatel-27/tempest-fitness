# Tempest-Fitness

Fitness tracking application to store and display user weight information, which can be sorted in ascending or descending order. The user must login to access their information. The exercise and workout components are a work in progress. 

# Directory Structure:

Tempest-Fitness
    ├── Tempest-Fitness
        ├── .gitattributes
        ├── .gitignore
        ├── .mvn
        │   └── wrapper
        │   │   └── maven-wrapper.properties
        ├── mvnw
        ├── mvnw.cmd
        ├── pom.xml
        └── src
        │   ├── main
        │       └── java
        │       │   └── com
        │       │       └── hpatel
        │       │           └── Tempest_Fitness
        │       │               ├── TempestFitnessApplication.java
        │       │               ├── config
        │       │                   ├── CustomUserDetailsService.java
        │       │                   ├── SecurityConfig.java
        │       │                   └── UserInitializer.java
        │       │               ├── controllers
        │       │                   ├── APIController.java
        │       │                   ├── AuthController.java
        │       │                   ├── ExerciseController.java
        │       │                   ├── UserController.java
        │       │                   ├── WeightController.java
        │       │                   └── WorkoutController.java
        │       │               ├── dto
        │       │                   └── LoginRequest.java
        │       │               ├── models
        │       │                   ├── DomainObject.java
        │       │                   ├── Exercise.java
        │       │                   ├── User.java
        │       │                   ├── Weight.java
        │       │                   └── Workout.java
        │       │               ├── repositories
        │       │                   ├── ExerciseRepository.java
        │       │                   ├── UserRepository.java
        │       │                   ├── WeightRepository.java
        │       │                   └── WorkoutRepository.java
        │       │               └── services
        │       │                   ├── ExerciseService.java
        │       │                   ├── Service.java
        │       │                   ├── UserService.java
        │       │                   ├── WeightService.java
        │       │                   └── WorkoutService.java
        │   └── test
        │       └── java
        │           └── com
        │               └── hpatel
        │                   └── Tempest_Fitness
        │                       ├── TempestFitnessApplicationTests.java
        │                       ├── TestConfig.java
        │                       └── models
        │                           ├── ExerciseTest.java
        │                           ├── UserTest.java
        │                           ├── WeightTest.java
        │                           └── WorkoutTest.java
    └── tempest-frontend
        ├── .gitignore
        ├── README.md
        ├── eslint.config.js
        ├── index.html
        ├── package-lock.json
        ├── package.json
        ├── src
            ├── App.jsx
            ├── assets
            │   └── storm_weather.png
            ├── components
            │   ├── Exercises.jsx
            │   ├── Home.jsx
            │   ├── Login.jsx
            │   ├── NavBar.jsx
            │   ├── Profile.jsx
            │   ├── ProtectedRoute.jsx
            │   ├── Register.jsx
            │   ├── Weights.jsx
            │   └── Workouts.jsx
            ├── contexts
            │   └── AuthContext.jsx
            ├── main.jsx
            ├── providers
            │   └── AuthProvider.jsx
            ├── services
            │   ├── exerciseService.js
            │   ├── userService.js
            │   ├── weightService.js
            │   └── workoutService.js
            └── styles
            │   ├── home.css
            │   ├── index.css
            │   ├── login.css
            │   ├── navbar.css
            │   ├── register.css
            │   └── weight.css
        └── vite.config.js
