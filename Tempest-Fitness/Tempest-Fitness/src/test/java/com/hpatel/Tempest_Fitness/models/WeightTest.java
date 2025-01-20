package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.services.WeightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest( classes = TestConfig.class )
public class WeightTest {

    /**
     * Service object that lets the Weights be saved to the db
     */
    @Autowired
    private WeightService service;

    @BeforeEach
    public void setup() {

    }

    /**
     * Test the construction of Weights
     */
    @Test
    @Transactional
    public void testConstructor() {
        final Weight w1 = new Weight( "2007-12-03", 175.0 );
        assertEquals( "2007-12-03", w1.getDate() );
        assertEquals( 175.0, w1.getWeight() );

        final Weight w2 = new Weight("2025-01-13", 178.2 );
        assertEquals( "2025-01-13", w2.getDate() );
        assertEquals( 178.2, w2.getWeight() );
    }

    @Test
    @Transactional
    public void testDates() {
        // check the dates to be sure they were set correctly
        final Weight w1 = new Weight("2025-01-13", 178.2 );
        assertEquals( "2025-01-13", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        final Weight w2 = new Weight( "2025-01-15", 154.5 );
        assertEquals( "2025-01-15", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );

        w1.setDate( "2025-01-02" );
        assertEquals( "2025-01-02", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        w2.setDate( "2025-12-31" );
        assertEquals( "2025-12-31", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );

    }

    @Test
    @Transactional
    public void testWeights() {
        final Weight w1 = new Weight("2025-01-19", 179.4 );
        final Weight w2 = new Weight( "2025-01-25", 184.3 );

        assertEquals( "2025-01-19", w1.getDate());
    }
}
