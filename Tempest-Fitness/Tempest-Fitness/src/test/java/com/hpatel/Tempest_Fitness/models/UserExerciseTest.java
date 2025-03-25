package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.services.UserExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@EnableAutoConfiguration
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class UserExerciseTest {

    /**
     * The service object that lets Exercises be saved to the db
     */
    @Autowired
    private UserExerciseService service;

    /**
     * Before each test delete everything from the service
     * so that the tests don't accidentally overlap data
     */
    @BeforeEach
    public void setup() {
        // Clean up the database before each test
        service.deleteAll();
    }

    /**
     * Ensure the constructor correctly creates an Exercise
     */
    @Test
    @Transactional
    public void testConstructor () {
//        final UserExercise e1 = new UserExercise( "Bench Press", 1, 10, 135.0 );
//
//        assertEquals( "Bench Press", e1.getName() );
//        assertEquals( 1, e1.getSets() );
//        assertEquals( 10, e1.getReps() );
//        assertEquals( 135.0, e1.getWeight() );
    }

    @Test
    @Transactional
    public void testSetName() {
//        final UserExercise e0 = new UserExercise( "Bench Press", 5, 6, 155.0 );
//
//        assertEquals( "Bench Press", e0.getName() );
//        assertEquals( 5, e0.getSets() );
//        assertEquals( 6, e0.getReps() );
//        assertEquals( 155.0, e0.getWeight() );
//
//        e0.setName( "Barbell Squat" );
//
//        assertEquals( "Barbell Squat", e0.getName() );
//        assertEquals( 5, e0.getSets() );
//        assertEquals( 6, e0.getReps() );
//        assertEquals( 155.0, e0.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidNames() {
//        Exception e1 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( null, 5, 6, 155.0 ));
//        Exception e2 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "", 5, 6, 155.0 ));
//        Exception e3 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "      ", 5, 6, 155.0 ));
//
//        assertEquals( "Exercise must have a name.", e1.getMessage() );
//        assertEquals( "Exercise must have a name.", e2.getMessage() );
//        assertEquals( "Exercise must have a name.", e3.getMessage() );

    }

    @Test
    @Transactional
    public void testSetSets() {
//        final UserExercise e2 = new UserExercise( "Bench Press", 5, 6, 155.0 );
//
//        assertEquals( "Bench Press", e2.getName() );
//        assertEquals( 5, e2.getSets() );
//        assertEquals( 6, e2.getReps() );
//        assertEquals( 155.0, e2.getWeight() );
//
//        e2.setSets( 20 );
//
//        assertEquals( "Bench Press", e2.getName() );
//        assertEquals( 20, e2.getSets() );
//        assertEquals( 6, e2.getReps() );
//        assertEquals( 155.0, e2.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidSets() {
//        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press", 0, 6, 155.0 ));
//        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press", 21, 6, 155.0 ));
//        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Squat", -1000, 6, 155.0 ));
//
//        assertEquals( "Sets value outside of expected range.", exc1.getMessage() );
//        assertEquals( "Sets value outside of expected range.", exc2.getMessage() );
//        assertEquals( "Sets value outside of expected range.", exc3.getMessage() );

    }

    @Test
    @Transactional
    public void testSetReps() {
//        final UserExercise e3 = new UserExercise( "Bench Press",2, 7, 145.7 );
//
//        assertEquals( "Bench Press", e3.getName() );
//        assertEquals( 2, e3.getSets() );
//        assertEquals( 7, e3.getReps() );
//        assertEquals( 145.7, e3.getWeight() );
//
//        e3.setReps( 1 );
//
//        assertEquals( "Bench Press", e3.getName() );
//        assertEquals( 2, e3.getSets() );
//        assertEquals( 1, e3.getReps() );
//        assertEquals( 145.7, e3.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidReps() {
//        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press", 3, 0, 155.0 ));
//        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press", 2, -1000, 115.0 ));
//        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Squat", 11, 101, 125.0 ));
//        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Squat", 4, 1000, 135.0 ));
//
//        assertEquals( "Reps value outside of expected range.", exc1.getMessage() );
//        assertEquals( "Reps value outside of expected range.", exc2.getMessage() );
//        assertEquals( "Reps value outside of expected range.", exc3.getMessage() );
//        assertEquals( "Reps value outside of expected range.", exc4.getMessage() );

    }

    @Test
    @Transactional
    public void testSetWeight() {
//        final UserExercise e4 = new UserExercise( "Bench Press",7, 9, 185.3 );
//
//        assertEquals( "Bench Press", e4.getName() );
//        assertEquals( 7, e4.getSets() );
//        assertEquals( 9, e4.getReps() );
//        assertEquals( 185.3, e4.getWeight() );
//
//        e4.setWeight( 0.0 );
//
//        assertEquals( "Bench Press", e4.getName() );
//        assertEquals( 7, e4.getSets() );
//        assertEquals( 9, e4.getReps() );
//        assertEquals( 0.0, e4.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidWeight() {
//        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press",7, 9, -0.1 ));
//        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Bench Press", 2, 3, -100.0 ));
//        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Squat", 11, 3, 500.1 ));
//        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new UserExercise( "Squat", 4, 1, 1000.0 ));
//
//        assertEquals( "Weight value outside of expected range.", exc1.getMessage() );
//        assertEquals( "Weight value outside of expected range.", exc2.getMessage() );
//        assertEquals( "Weight value outside of expected range.", exc3.getMessage() );
//        assertEquals( "Weight value outside of expected range.", exc4.getMessage() );
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    @Transactional
    public void testEquals() {
//        final UserExercise e10 = new UserExercise( "Bench Press",5, 5, 136.0 );
//        final UserExercise e11 = new UserExercise( "Bench Press",2, 3, 186.4 );
//
//        assertNotEquals( e10, e11 );
//
//        // Check that the equals method does not indicate equality
//        // for a different type of class
//        User u1 = new User();
//        assertNotEquals( e10, u1 );
//
//        // Check that the same object is considered equal
//        assertEquals( e10, e10 );
//
//        // If the name and the weight of the exercise is the same
//        // they should be equal
//        final UserExercise e12 = new UserExercise( "Bench Press",7, 1, 136.0 );
//        assertEquals( e10, e12 );
//
//        // Check that it is not equal compared to a null
//        assertNotEquals(null, e12);
    }

    @Test
    @Transactional
    public void testToString() {
//        final UserExercise e10 = new UserExercise( "Bench Press",5, 5, 136.0 );
//        assertEquals( "Exercise{name=Bench Press, sets=5, reps=5, weight=136.0}", e10.toString() );
//
//        final UserExercise e11 = new UserExercise( "Deadlift",2, 3, 186.4 );
//        assertEquals( "Exercise{name=Deadlift, sets=2, reps=3, weight=186.4}", e11.toString() );

    }

    @Test
    @Transactional
    public void testSaveToDB() {
//        final UserExercise userExercise1 = new UserExercise("Deadlift", 1, 1, 315.0 );
//
//        assertEquals( 0, service.count() );
//        service.save(userExercise1);
//        assertEquals( 1, service.count());
//
//        List<UserExercise> list = service.findAll();
//
//        assertEquals( 1, list.size());
//
//        UserExercise listE1 = list.getFirst();
//        assertEquals( listE1, userExercise1);
//        assertEquals( listE1.getName(), userExercise1.getName() );
//        assertEquals( listE1.getSets(), userExercise1.getSets() );
//        assertEquals( listE1.getReps(), userExercise1.getReps() );
//        assertEquals( listE1.getWeight(), userExercise1.getWeight() );

    }

    /**
     * Tests the hashing of the objects
     */
    @Test
    @Transactional
    public void testHashing() {
//        final UserExercise e0 = new UserExercise( "Bench Press",5, 5, 136.0 );
//        final UserExercise e1 = new UserExercise( "Bench Press",2, 3, 186.4 );
//        final UserExercise e2 = new UserExercise( "Bench Press",5, 5, 136.0 );
//
//        assertEquals( e0.hashCode(), e2.hashCode() );
//        assertNotEquals( e0.hashCode(), e1.hashCode() );
    }
}
