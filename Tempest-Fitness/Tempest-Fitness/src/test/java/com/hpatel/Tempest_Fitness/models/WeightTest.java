package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.services.UserService;
import com.hpatel.Tempest_Fitness.services.WeightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest( classes = TestConfig.class )
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class WeightTest {

    /**
     * Service object that lets the Weights be saved to the db
     */
    @Autowired
    private WeightService weightService;

    /**
     * User Service object that lets the Users be saved to the database
     */
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        weightService.deleteAll();
    }

    /**
     * Test the construction of Weights
     */
    @Test
    @Transactional
    public void testConstructor() {
        User u1 = new User();
        u1.setUsername("henry");
        u1.setPassword("pw");
        u1.setRole("CUSTOMER");

        final Weight w1 = new Weight( "2007-12-03", 175.0, u1 );
        assertEquals( "2007-12-03", w1.getDate() );
        assertEquals( 175.0, w1.getWeight() );

        final Weight w2 = new Weight("2025-01-13", 178.2, u1);
        assertEquals( "2025-01-13", w2.getDate() );
        assertEquals( 178.2, w2.getWeight() );

        final Weight emptyWeight = new Weight();
        // When a double is not initialized it should be at 0
        assertEquals( 0.0, emptyWeight.getWeight() );
        assertNull(emptyWeight.getDate());

        final Weight oneParamWeight = new Weight( 198.3, u1 );
        assertEquals( LocalDate.now().toString(), oneParamWeight.getDate() );
        assertEquals( 198.3, oneParamWeight.getWeight() );
    }

    @Test
    @Transactional
    public void testSetDates() {
        // Make a default user to use for making Weight objects
        User defaultUser = new User();
        defaultUser.setUsername("harsh");
        defaultUser.setPassword("pw");
        defaultUser.setRole("TESTER");

        // check the dates to be sure they were set correctly
        final Weight w1 = new Weight("2025-01-13", 178.2, defaultUser );
        assertEquals( "2025-01-13", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        final Weight w2 = new Weight( "2025-01-15", 154.5, defaultUser );
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
        // Make a default user to use for making Weight objects
        User defaultUser = new User();
        defaultUser.setUsername("harsh");
        defaultUser.setPassword("pw");
        defaultUser.setRole("TESTER");

        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new Weight( null, -0.1, defaultUser ));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new Weight( "", -1000.0, defaultUser ));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-1", 1000.1, defaultUser ));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-140", 1500.0, defaultUser ));

        assertEquals( "Date of the weigh-in is not valid.", exc1.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc2.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc3.getMessage() );
        assertEquals( "Date of the weigh-in is not valid.", exc4.getMessage() );

    }

    @Test
    @Transactional
    public void testWeights() {
        // Make a default user to use for making Weight objects
        User defaultUser = new User();
        defaultUser.setUsername("harsh");
        defaultUser.setPassword("pw");
        defaultUser.setRole("TESTER");

        final Weight w1 = new Weight("2025-01-19", 178.2, defaultUser );
        assertEquals( "2025-01-19", w1.getDate() );
        assertEquals( 178.2, w1.getWeight() );

        final Weight w2 = new Weight( "2025-01-25", 154.5, defaultUser );
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
        // Make a default user to use for making Weight objects
        User defaultUser = new User();
        defaultUser.setUsername("harsh");
        defaultUser.setPassword("pw");
        defaultUser.setRole("TESTER");

        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-11", -0.1, defaultUser ));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-12", -1000.0, defaultUser ));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-13", 1000.1, defaultUser ));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-14", 1500.0, defaultUser ));

        assertEquals( "Weight value outside of expected range.", exc1.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc2.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc3.getMessage() );
        assertEquals( "Weight value outside of expected range.", exc4.getMessage() );

    }

    @Test
    @Transactional
    public void testUsers() {
        // Make a default user to use for making Weight objects
        User testUser = new User();
        testUser.setUsername("harsh");
        testUser.setPassword("pw");
        testUser.setRole("TESTER");

        assertEquals("harsh", testUser.getUsername());
        assertEquals("pw", testUser.getPassword());
        assertEquals("TESTER", testUser.getRole());

        // Sets user
        Weight w1 = new Weight("2025-03-12", 173.2, testUser );
        assertEquals("2025-03-12", w1.getDate());
        assertEquals(173.2, w1.getWeight());

        // Tests that it's the same user
        assertEquals(testUser, w1.getUser());

        // Change other weight info and make sure nothing about the user is changed
        w1.setWeight( 1000.0 );
        w1.setDate("2025-02-17");
        assertEquals("2025-02-17", w1.getDate());
        assertEquals(1000.0, w1.getWeight());

        assertEquals(testUser, w1.getUser());
        assertEquals(testUser.getUsername(), w1.getUser().getUsername());
        assertEquals(testUser.getPassword(), w1.getUser().getPassword());
        assertEquals(testUser.getRole(), w1.getUser().getRole());
    }


    @Test
    @Transactional
    public void testInvalidUser() {
        // Make a default user to use for testing
        User testUser1 = new User();

        User testUser3 = new User();
        testUser3.setUsername("test");


        User testUser5 = new User();
        testUser5.setUsername("test");
        testUser5.setPassword("test");



        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> new Weight( "2025-01-11", 151.4, null ));
        Exception exc2 = assertThrows( NullPointerException.class, () -> new Weight( "2025-01-12", 178.3, testUser1 ));
        Exception exc4 = assertThrows( NullPointerException.class, () -> new Weight( "2025-01-14", 126.0, testUser3 ));
        Exception exc6 = assertThrows( NullPointerException.class, () -> new Weight( "2025-01-14", 126.0, testUser5 ));

        assertEquals( "User was not provided with the Weight.", exc1.getMessage() );
        assertEquals( "Username was not provided.", exc2.getMessage() );
        assertEquals( "Password was not provided.", exc4.getMessage() );
        assertEquals( "Role was not provided.", exc6.getMessage() );

    }


    @Test
    @Transactional
    public void testToString() {
        // Make a user for each of the test options
        User testUser1 = new User();
        testUser1.setUsername("testUser1");
        testUser1.setPassword("test");
        testUser1.setRole("TEST");

        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("test");
        testUser2.setRole("TEST");

        User testUser3 = new User();
        testUser3.setUsername("testUser3");
        testUser3.setPassword("test");
        testUser3.setRole("TEST");

        final Weight weight1 = new Weight( "2025-01-20", 178.3, testUser1 );
        final Weight weight2 = new Weight( "2025-01-27", 173.2, testUser2 );
        final Weight weight3 = new Weight( "2025-01-29", 181.4, testUser3 );

        assertEquals( "Weight{date='2025-01-20', weight='178.3', user='testUser1'}", weight1.toString() );
        assertEquals( "Weight{date='2025-01-27', weight='173.2', user='testUser2'}", weight2.toString() );
        assertEquals( "Weight{date='2025-01-29', weight='181.4', user='testUser3'}", weight3.toString() );
    }

    @Test
    @Transactional
    public void testSaveToDB() {
        // Make a default user to use for making Weight objects
        User testUser = new User();
        testUser.setUsername("testUser1");
        testUser.setPassword("test1");
        testUser.setRole("TEST");

        // To save weights with a user, the associated user must also be saved to the db
        userService.save(testUser);

        final Weight weight1 = new Weight( "2025-01-20", 178.3, testUser );

        assertEquals( 0, weightService.count() );
        weightService.save( weight1 );
        assertEquals( 1, weightService.count() );

        List<Weight> list = weightService.findAll();
        assertEquals( 1, list.size() );

        Weight listW1 = list.getFirst();
        assertEquals( listW1, weight1 );
        assertEquals( listW1.getDate(), weight1.getDate() );
        assertEquals( listW1.getWeight(), weight1.getWeight() );

        final Weight weight2 = new Weight( "2025-01-27", 173.2, testUser );
        final Weight weight3 = new Weight( "2025-01-29", 181.4, testUser );

        weightService.save( weight2 );
        weightService.save( weight3 );
        assertEquals( 3, weightService.count() );

        list = weightService.findAll();
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
        // Make a default user to use for making Weight objects
        User testUser = new User();
        testUser.setUsername("testUser1");
        testUser.setPassword("test1");
        testUser.setRole("TEST");

        final Weight w0 = new Weight( "2025-02-14",167.5, testUser );
        final Weight w1 = new Weight( "2025-05-17",152.1, testUser );

        assertNotEquals( w0, w1 );

        // Check that the equals method does not indicate equality
        // for a different type of class
        final Exercise e1 = new Exercise( "Bench Press",5, 5, 136.0 );
        assertNotEquals( w0, e1 );

        // Check that the same object is considered equal
        assertEquals( w0, w0 );

        // If the date and the weight of the Weight is the same
        // they should be equal
        final Weight w2 = new Weight( "2025-02-14",167.5, testUser );
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
        // Make a user for each of the test options
        User testUser1 = new User();
        testUser1.setUsername("testUser1");
        testUser1.setPassword("test1");
        testUser1.setRole("TEST");

        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("test2");
        testUser2.setRole("TEST");


        // Making some weights to test the hashcode comparisons
        final Weight w0 = new Weight("2025-04-12", 136.1, testUser1);
        final Weight w1 = new Weight("2025-11-31", 156.0, testUser1);
        final Weight w2 = new Weight("2025-04-12", 136.1, testUser1);
        final Weight w3 = new Weight("2025-03-11", 136.1, testUser2);
        final Weight w4 = new Weight("2025-04-12", 156.1, testUser2);

        // Test if two weights with the same date, weight, and user have the same code
        assertEquals( w0.hashCode(), w2.hashCode() );

        // Test if two weights with different date, weight, but same user are going to have
        // different hashes
        assertNotEquals( w0.hashCode(), w1.hashCode() );

        // Test if two weights with different date, user, but same weight are going to have
        // different hashes
        assertNotEquals( w0.hashCode(), w3.hashCode() );

        // Test if two weights with different weight, user, but same date are going to have
        // different hashes
        assertNotEquals( w0.hashCode(), w3.hashCode() );
    }
}
