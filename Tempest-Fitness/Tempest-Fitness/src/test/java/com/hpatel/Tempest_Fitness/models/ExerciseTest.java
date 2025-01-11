package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.repositories.ExerciseRepository;
import com.hpatel.Tempest_Fitness.services.ExerciseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableAutoConfiguration
@SpringBootTest( classes = TestConfig.class )
public class ExerciseTest {

    @Autowired
    private ExerciseService service;

    @BeforeEach
    public void setup() {

    }

    /**
     * Ensure the constructor correctly creates an ingredient
     */
    @Test
    @Transactional
    public void testConstructor () {
        final Exercise e1 = new Exercise( "Bench Press", 1, 10, 135.0 );

        Assertions.assertEquals( "Bench Press", e1.getName() );
        Assertions.assertEquals( 1, e1.getSets() );
        Assertions.assertEquals( 10, e1.getReps() );
        Assertions.assertEquals( 135.0, e1.getWeight() );
    }

    @Test
    @Transactional
    public void testSetName() {
        final Exercise e0 = new Exercise( "Bench Press", 5, 6, 155.0 );

        Assertions.assertEquals( "Bench Press", e0.getName() );
        Assertions.assertEquals( 5, e0.getSets() );
        Assertions.assertEquals( 6, e0.getReps() );
        Assertions.assertEquals( 155.0, e0.getWeight() );

        e0.setName( "Barbell Squat" );

        Assertions.assertEquals( "Barbell Squat", e0.getName() );
        Assertions.assertEquals( 5, e0.getSets() );
        Assertions.assertEquals( 6, e0.getReps() );
        Assertions.assertEquals( 155.0, e0.getWeight() );

    }

    @Test
    @Transactional
    public void testSetSets() {
        final Exercise e2 = new Exercise( "Bench Press", 5, 6, 155.0 );

        Assertions.assertEquals( "Bench Press", e2.getName() );
        Assertions.assertEquals( 5, e2.getSets() );
        Assertions.assertEquals( 6, e2.getReps() );
        Assertions.assertEquals( 155.0, e2.getWeight() );

        e2.setSets( 3 );

        Assertions.assertEquals( "Bench Press", e2.getName() );
        Assertions.assertEquals( 3, e2.getSets() );
        Assertions.assertEquals( 6, e2.getReps() );
        Assertions.assertEquals( 155.0, e2.getWeight() );

    }

    @Test
    @Transactional
    public void testSetReps() {
        final Exercise e3 = new Exercise( "Bench Press",2, 7, 145.7 );

        Assertions.assertEquals( "Bench Press", e3.getName() );
        Assertions.assertEquals( 2, e3.getSets() );
        Assertions.assertEquals( 7, e3.getReps() );
        Assertions.assertEquals( 145.7, e3.getWeight() );

        e3.setReps( 3 );

        Assertions.assertEquals( "Bench Press", e3.getName() );
        Assertions.assertEquals( 2, e3.getSets() );
        Assertions.assertEquals( 3, e3.getReps() );
        Assertions.assertEquals( 145.7, e3.getWeight() );

    }

    @Test
    @Transactional
    public void testSetWeight() {
        final Exercise e4 = new Exercise( "Bench Press",7, 9, 185.3 );

        Assertions.assertEquals( "Bench Press", e4.getName() );
        Assertions.assertEquals( 7, e4.getSets() );
        Assertions.assertEquals( 9, e4.getReps() );
        Assertions.assertEquals( 185.3, e4.getWeight() );

        e4.setWeight( 125.1 );

        Assertions.assertEquals( "Bench Press", e4.getName() );
        Assertions.assertEquals( 7, e4.getSets() );
        Assertions.assertEquals( 9, e4.getReps() );
        Assertions.assertEquals( 125.1, e4.getWeight() );

    }

    @Test
    @Transactional
    public void testEquals() {
        final Exercise e10 = new Exercise( "Bench Press",5, 5, 136.0 );
        final Exercise e11 = new Exercise( "Bench Press",2, 3, 186.4 );

        Assertions.assertNotEquals( e10, e11 );

    }

    @Test
    @Transactional
    public void testToString() {
        final Exercise e10 = new Exercise( "Bench Press",5, 5, 136.0 );
        Assertions.assertEquals( "Exercise{name=Bench Press, sets=5, reps=5, weight=136.0}", e10.toString() );

        final Exercise e11 = new Exercise( "Deadlift",2, 3, 186.4 );
        Assertions.assertEquals( "Exercise{name=Deadlift, sets=2, reps=3, weight=186.4}", e11.toString() );

    }

    @Test
    @Transactional
    public void testSaveToDB() {
        final Exercise exercise1 = new Exercise("Deadlift", 1, 1, 315.0 );

        Assertions.assertEquals( 0, service.count() );
        service.save( exercise1 );
        Assertions.assertEquals( 1, service.count());

        List<Exercise> list = service.findAll();

        Assertions.assertEquals( 1, list.size());

        Exercise listE1 = list.getFirst();
        Assertions.assertEquals( listE1, exercise1 );
        Assertions.assertEquals( listE1.getName(), exercise1.getName() );
        Assertions.assertEquals( listE1.getSets(), exercise1.getSets() );
        Assertions.assertEquals( listE1.getReps(), exercise1.getReps() );
        Assertions.assertEquals( listE1.getWeight(), exercise1.getWeight() );

    }
}
