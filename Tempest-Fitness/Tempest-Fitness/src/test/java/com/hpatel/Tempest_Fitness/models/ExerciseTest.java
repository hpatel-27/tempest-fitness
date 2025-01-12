package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.services.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@EnableAutoConfiguration
@SpringBootTest( classes = TestConfig.class )
public class ExerciseTest {

    /**
     * The service object that lets Exercises be saved to the db
     */
    @Autowired
    private ExerciseService service;

    /**
     * Before each test delete everything from the service
     * so that the tests don't accidentally overlap data
     */
    @BeforeEach
    public void setup() {

    }

    /**
     * Ensure the constructor correctly creates an Exercise
     */
    @Test
    @Transactional
    public void testConstructor () {
        final Exercise e1 = new Exercise( "Bench Press", 1, 10, 135.0 );

        assertEquals( "Bench Press", e1.getName() );
        assertEquals( 1, e1.getSets() );
        assertEquals( 10, e1.getReps() );
        assertEquals( 135.0, e1.getWeight() );
    }

    @Test
    @Transactional
    public void testSetName() {
        final Exercise e0 = new Exercise( "Bench Press", 5, 6, 155.0 );

        assertEquals( "Bench Press", e0.getName() );
        assertEquals( 5, e0.getSets() );
        assertEquals( 6, e0.getReps() );
        assertEquals( 155.0, e0.getWeight() );

        e0.setName( "Barbell Squat" );

        assertEquals( "Barbell Squat", e0.getName() );
        assertEquals( 5, e0.getSets() );
        assertEquals( 6, e0.getReps() );
        assertEquals( 155.0, e0.getWeight() );

    }

    @Test
    @Transactional
    public void testSetSets() {
        final Exercise e2 = new Exercise( "Bench Press", 5, 6, 155.0 );

        assertEquals( "Bench Press", e2.getName() );
        assertEquals( 5, e2.getSets() );
        assertEquals( 6, e2.getReps() );
        assertEquals( 155.0, e2.getWeight() );

        e2.setSets( 3 );

        assertEquals( "Bench Press", e2.getName() );
        assertEquals( 3, e2.getSets() );
        assertEquals( 6, e2.getReps() );
        assertEquals( 155.0, e2.getWeight() );

    }

    @Test
    @Transactional
    public void testSetReps() {
        final Exercise e3 = new Exercise( "Bench Press",2, 7, 145.7 );

        assertEquals( "Bench Press", e3.getName() );
        assertEquals( 2, e3.getSets() );
        assertEquals( 7, e3.getReps() );
        assertEquals( 145.7, e3.getWeight() );

        e3.setReps( 3 );

        assertEquals( "Bench Press", e3.getName() );
        assertEquals( 2, e3.getSets() );
        assertEquals( 3, e3.getReps() );
        assertEquals( 145.7, e3.getWeight() );

    }

    @Test
    @Transactional
    public void testSetWeight() {
        final Exercise e4 = new Exercise( "Bench Press",7, 9, 185.3 );

        assertEquals( "Bench Press", e4.getName() );
        assertEquals( 7, e4.getSets() );
        assertEquals( 9, e4.getReps() );
        assertEquals( 185.3, e4.getWeight() );

        e4.setWeight( 125.1 );

        assertEquals( "Bench Press", e4.getName() );
        assertEquals( 7, e4.getSets() );
        assertEquals( 9, e4.getReps() );
        assertEquals( 125.1, e4.getWeight() );

    }

    @Test
    @Transactional
    public void testEquals() {
        final Exercise e10 = new Exercise( "Bench Press",5, 5, 136.0 );
        final Exercise e11 = new Exercise( "Bench Press",2, 3, 186.4 );

        assertNotEquals( e10, e11 );

        // Check that the equals method does not indicate equality
        // for a different type of class
        final Weight w = new Weight(LocalDate.now().toString(), 150.0 );
        assertNotEquals( e10, w );

        // Check that the same object is considered equal
        assertEquals( e10, e10 );

        // If the name and the weight of the exercise is the same
        // they should be equal
        final Exercise e12 = new Exercise( "Bench Press",7, 1, 136.0 );
        assertEquals( e10, e12 );

        // Check that it is not equal compared to a null
        assertNotEquals( e12, null );
    }

    @Test
    @Transactional
    public void testToString() {
        final Exercise e10 = new Exercise( "Bench Press",5, 5, 136.0 );
        assertEquals( "Exercise{name=Bench Press, sets=5, reps=5, weight=136.0}", e10.toString() );

        final Exercise e11 = new Exercise( "Deadlift",2, 3, 186.4 );
        assertEquals( "Exercise{name=Deadlift, sets=2, reps=3, weight=186.4}", e11.toString() );

    }

    @Test
    @Transactional
    public void testSaveToDB() {
        final Exercise exercise1 = new Exercise("Deadlift", 1, 1, 315.0 );

        assertEquals( 0, service.count() );
        service.save( exercise1 );
        assertEquals( 1, service.count());

        List<Exercise> list = service.findAll();

        assertEquals( 1, list.size());

        Exercise listE1 = list.getFirst();
        assertEquals( listE1, exercise1 );
        assertEquals( listE1.getName(), exercise1.getName() );
        assertEquals( listE1.getSets(), exercise1.getSets() );
        assertEquals( listE1.getReps(), exercise1.getReps() );
        assertEquals( listE1.getWeight(), exercise1.getWeight() );

    }

    /**
     * Tests the hashing of the objects
     */
    @Test
    @Transactional
    public void testHashing() {
        final Exercise e0 = new Exercise( "Bench Press",5, 5, 136.0 );
        final Exercise e1 = new Exercise( "Bench Press",2, 3, 186.4 );
        final Exercise e2 = new Exercise( "Bench Press",5, 5, 136.0 );

        assertEquals( e0.hashCode(), e2.hashCode() );
        assertNotEquals( e0.hashCode(), e1.hashCode() );
    }
}
