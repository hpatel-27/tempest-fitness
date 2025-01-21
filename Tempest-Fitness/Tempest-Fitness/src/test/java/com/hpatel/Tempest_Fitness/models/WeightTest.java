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
import java.util.List;

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

        final Weight emptyWeight = new Weight();
        // When a double is not initialized it should be at 0
        assertEquals( 0.0, emptyWeight.getWeight() );
        assertNull(emptyWeight.getDate());

        final Weight oneParamWeight = new Weight( 198.3 );
        assertEquals( LocalDate.now().toString(), oneParamWeight.getDate() );
        assertEquals( 198.3, oneParamWeight.getWeight() );
    }

    @Test
    @Transactional
    public void testSetDates() {
        // check the dates to be sure they were set correctly
        final Weight w1 = new Weight("2025-01-13", 178.2 );
        assertEquals( "2025-01-13", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        final Weight w2 = new Weight( "2025-01-15", 154.5 );
        assertEquals( "2025-01-15", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );

        // test the first date of the year
        w1.setDate( "2025-01-01" );
        assertEquals( "2025-01-01", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        // test the last date of the year
        w2.setDate( "2025-12-31" );
        assertEquals( "2025-12-31", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );

        // test somewhere between
        w2.setDate( "2025-06-17" );
        assertEquals( "2025-06-17", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidDates() {
        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new Weight( null, -0.1 ));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new Weight( "", -1000.0 ));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-1", 1000.1 ));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-140", 1500.0 ));

        assertEquals( "Date of the weigh-in is not valid.", exc1.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc2.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc3.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc4.getMessage() );

    }

    @Test
    @Transactional
    public void testWeights() {
        final Weight w1 = new Weight("2025-01-19", 178.2 );
        assertEquals( "2025-01-19", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        final Weight w2 = new Weight( "2025-01-25", 154.5 );
        assertEquals( "2025-01-25", w2.getDate() );
        assertEquals( 154.5, w2.getWeight() );

        // test the lowest value
        w1.setWeight( 0.0 );
        assertEquals( "2025-01-19", w1.getDate() );
        assertEquals( 0.0, w1.getWeight() );

        // test the highest value
        w2.setWeight( 1000.0 );
        assertEquals( "2025-01-25", w2.getDate() );
        assertEquals( 1000.0, w2.getWeight() );

        // test within the range
        w2.setWeight( 518.8 );
        assertEquals( 518.8, w2.getWeight() );
    }

    @Test
    @Transactional
    public void testInvalidWeights() {
        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-11", -0.1 ));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-12", -1000.0 ));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-13", 1000.1 ));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-14", 1500.0 ));

        assertEquals( "Weight value outside of expected range.", exc1.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc2.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc3.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc4.getMessage() );

    }

    @Test
    @Transactional
    public void testToString() {
        final Weight weight1 = new Weight( "2025-01-20", 178.3 );
        final Weight weight2 = new Weight( "2025-01-27", 173.2 );
        final Weight weight3 = new Weight( "2025-01-29", 181.4 );

        assertEquals( "Weight{date='2025-01-20', weight=178.3}", weight1.toString() );
        assertEquals( "Weight{date='2025-01-27', weight=173.2}", weight2.toString() );
        assertEquals( "Weight{date='2025-01-29', weight=181.4}", weight3.toString() );

    }
    @Test
    @Transactional
    public void testSaveToDB() {
        final Weight weight1 = new Weight( "2025-01-20", 178.3 );

        assertEquals( 0, service.count() );
        service.save( weight1 );
        assertEquals( 1, service.count() );

        List<Weight> list = service.findAll();
        assertEquals( 1, list.size() );

        Weight listW1 = list.getFirst();
        assertEquals( listW1, weight1 );
        assertEquals( listW1.getDate(), weight1.getDate() );
        assertEquals( listW1.getWeight(), weight1.getWeight() );

        final Weight weight2 = new Weight( "2025-01-27", 173.2 );
        final Weight weight3 = new Weight( "2025-01-29", 181.4 );

        service.save( weight2 );
        service.save( weight3 );
        assertEquals( 3, service.count() );

        list = service.findAll();
        assertEquals( 3, list.size() );

        Weight w1 = list.get(0);
        Weight w2 = list.get(1);
        Weight w3 = list.get(2);
        assertEquals( w1, weight1 );
        assertEquals( w2, weight2 );
        assertEquals( w3, weight3 );
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    @Transactional
    public void testEquals() {
        final Weight w0 = new Weight( "2025-02-14",167.5 );
        final Weight w1 = new Weight( "2025-05-17",152.1 );

        assertNotEquals( w0, w1 );

        // Check that the equals method does not indicate equality
        // for a different type of class
        final Exercise e1 = new Exercise( "Bench Press",5, 5, 136.0 );
        assertNotEquals( w0, e1 );

        // Check that the same object is considered equal
        assertEquals( w0, w0 );

        // If the date and the weight of the Weight is the same
        // they should be equal
        final Weight w2 = new Weight( "2025-02-14",167.5 );
        assertEquals( w0, w2 );

        // Check that it is not equal compared to a null
        assertNotEquals(null, w2);
    }

    /**
     * Tests the hashing of the objects
     */
    @Test
    @Transactional
    public void testHashing() {
        final Weight w0 = new Weight( "2025-04-12", 136.1 );
        final Weight w1 = new Weight( "2025-11-31", 156.0 );
        final Weight w2 = new Weight( "2025-04-12", 136.1 );

        assertEquals( w0.hashCode(), w2.hashCode() );
        assertNotEquals( w0.hashCode(), w1.hashCode() );
    }
}
