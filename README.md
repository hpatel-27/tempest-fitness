# Tempest-Fitness

Fitness tracking application to store and display user weight information, which can be sorted in ascending or descending order. The user must login to access their information. The exercise and workout components are a work in progress. 

# Directory Structure:
```
Tempest-Fitness/
├── tempest-backend/          # Spring Boot Backend
│   ├── .mvn/                 # Maven Wrapper
│   ├── mvnw, mvnw.cmd        # Maven Wrapper Scripts
│   ├── pom.xml               # Maven Project Configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hpatel/Tempest_Fitness/
│   │   │   │   ├── TempestFitnessApplication.java  # Main Spring Boot Application
│   │   │   │   ├── config/                         # Security Configuration
│   │   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── UserInitializer.java
│   │   │   │   ├── controllers/                    # API Controllers
│   │   │   │   │   ├── APIController.java
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── ExerciseController.java
│   │   │   │   │   ├── UserController.java
│   │   │   │   │   ├── WeightController.java
│   │   │   │   │   ├── WorkoutController.java
│   │   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── models/                         # Database Models
│   │   │   │   │   ├── DomainObject.java
│   │   │   │   │   ├── Exercise.java
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Weight.java
│   │   │   │   │   ├── Workout.java
│   │   │   │   ├── repositories/                   # Data Repositories
│   │   │   │   │   ├── ExerciseRepository.java
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── WeightRepository.java
│   │   │   │   │   ├── WorkoutRepository.java
│   │   │   │   ├── services/                       # Business Logic
│   │   │   │   │   ├── ExerciseService.java
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   ├── WeightService.java
│   │   │   │   │   ├── WorkoutService.java
│   │   ├── test/java/com/hpatel/Tempest_Fitness/   # Unit Tests
│   │   │   ├── TempestFitnessApplicationTests.java
│   │   │   ├── TestConfig.java
│   │   │   ├── models/
│   │   │   │   ├── ExerciseTest.java
│   │   │   │   ├── UserTest.java
│   │   │   │   ├── WeightTest.java
│   │   │   │   ├── WorkoutTest.java
│
├── tempest-frontend/          # React Frontend
│   ├── .gitignore
│   ├── README.md
│   ├── eslint.config.js
│   ├── vite.config.js         # Vite Configuration
│   ├── package.json           # Project Dependencies
│   ├── package-lock.json
│   ├── index.html
│   ├── src/
│   │   ├── App.jsx            # Main React Component
│   │   ├── main.jsx           # Entry Point
│   │   ├── assets/            # Static Assets
│   │   │   ├── storm_weather.png
│   │   ├── components/        # Reusable Components
│   │   │   ├── Exercises.jsx
│   │   │   ├── Home.jsx
│   │   │   ├── Login.jsx
│   │   │   ├── NavBar.jsx
│   │   │   ├── Profile.jsx
│   │   │   ├── ProtectedRoute.jsx
│   │   │   ├── Register.jsx
│   │   │   ├── Weights.jsx
│   │   │   ├── Workouts.jsx
│   │   ├── contexts/          # React Contexts
│   │   │   ├── AuthContext.jsx
│   │   ├── providers/         # Context Providers
│   │   │   ├── AuthProvider.jsx
│   │   ├── services/          # API Services
│   │   │   ├── exerciseService.js
│   │   │   ├── userService.js
│   │   │   ├── weightService.js
│   │   │   ├── workoutService.js
│   │   ├── styles/            # CSS Styles
│   │   │   ├── home.css
│   │   │   ├── index.css
│   │   │   ├── login.css
│   │   │   ├── navbar.css
│   │   │   ├── register.css
│   │   │   ├── weight.css
```
