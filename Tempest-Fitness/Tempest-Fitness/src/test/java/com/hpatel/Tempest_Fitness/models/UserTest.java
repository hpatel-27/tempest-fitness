package com.hpatel.Tempest_Fitness.models;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableAutoConfiguration
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class UserTest {

    /**
     * User service object to interact with the User objects
     */
    @Autowired
    private UserService service;

    /**
     * Deletes all users before each test
     */
    @BeforeEach
    public void setup() {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testSaveUser() {
        final User u1 = new User();
        u1.setUsername("user1");
        u1.setPassword("pw1");
        u1.setRole("TEST");
        service.save(u1);

        final User u2 = new User();
        u2.setUsername("user2");
        u2.setPassword("pw2");
        u2.setRole("TEST");
        service.save(u2);

        final List<User> users = service.findAll();
        assertEquals(2, users.size(), "Creating two users should have resulted in two users" +
                "being saved to the database");
        // Check the first user as being the same after being saved
        assertEquals(u1, users.getFirst());
        assertEquals(u1.getUsername(), users.getFirst().getUsername());
        assertEquals(u1.getPassword(), users.getFirst().getPassword());
        assertEquals(u1.getRole(), users.getFirst().getRole());

        // Check that the second user is the same after being saved
        assertEquals(u2, users.getLast());
        assertEquals(u2.getUsername(), users.getLast().getUsername());
        assertEquals(u2.getPassword(), users.getLast().getPassword());
        assertEquals(u2.getRole(), users.getLast().getRole());

    }

    /**
     * Tests the functionality to delete users
     */
    @Test
    @Transactional
    public void testDeleteUser() {
        assertEquals(0, service.findAll().size(), "There should be no users at the start of the test.");

        final User u1 = new User();
        u1.setUsername("user1");
        u1.setPassword("pw1");
        u1.setRole("TEST");
        service.save(u1);

        final User u2 = new User();
        u2.setUsername("user2");
        u2.setPassword("pw2");
        u2.setRole("TEST");
        service.save(u2);

        final User u3 = new User();
        u3.setUsername("user3");
        u3.setPassword("pw3");
        u3.setRole("TEST");
        service.save(u3);

        List<User> users = service.findAll();

        assertEquals(3, service.count());
        assertEquals(u1, users.getFirst());
        assertEquals(u2, users.get(1));
        assertEquals(u3, users.getLast());

        service.delete(u1);
        users = service.findAll();
        assertEquals(2, service.count());
        assertEquals(u2, users.getFirst());
        assertEquals(u3, users.getLast());

        service.delete(u2);
        users = service.findAll();
        assertEquals(1, service.count());
        assertEquals(u3, users.getFirst());

        service.deleteAll();
        users = service.findAll();
        assertEquals(0, service.count());
        assertEquals(0, users.size());
    }

    @Test
    @Transactional
    public void testUsername() {
        User u1 = new User();
        u1.setUsername("testuser1");
        u1.setPassword("testpw1");
        u1.setRole("USER");

        assertEquals("testuser1", u1.getUsername());
        assertEquals("testpw1", u1.getPassword());
        assertEquals("USER", u1.getRole());

        u1.setUsername("changeduser");

        assertEquals("changeduser", u1.getUsername());
        assertEquals("testpw1", u1.getPassword());
        assertEquals("USER", u1.getRole());

        // Test username at max bound
        u1.setUsername("hhhhhhhhhhhhhhhhhhhhhhhhh");
        assertEquals(25, u1.getUsername().length());

        // Testing minimum bound
        u1.setUsername("h");
        assertEquals(1, u1.getUsername().length());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( NullPointerException.class, () -> errorUser.setUsername(null) );
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setUsername(""));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setUsername("hhhhhhhhhhhhhhhhhhhhhhhhha"));

        assertEquals( "No username was provided.", exc1.getMessage() );
        assertEquals( "Empty username was provided.", exc2.getMessage() );
        assertEquals( "Username cannot be more than 25 characters.", exc3.getMessage() );

    }

    @Test
    @Transactional
    public void testPassword() {
        User u1 = new User();
        u1.setUsername("testuser1");
        u1.setPassword("testpw1");
        u1.setRole("USER");

        assertEquals("testuser1", u1.getUsername());
        assertEquals("testpw1", u1.getPassword());
        assertEquals("USER", u1.getRole());

        u1.setPassword("changedpassword");

        assertEquals("testuser1", u1.getUsername());
        assertEquals("changedpassword", u1.getPassword());
        assertEquals("USER", u1.getRole());

        // Test password at max bound
        u1.setPassword("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        assertEquals(100, u1.getPassword().length());

        // Testing minimum bound
        u1.setPassword("h");
        assertEquals(1, u1.getPassword().length());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( NullPointerException.class, () -> errorUser.setPassword(null) );
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setPassword(""));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setPassword("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhha"));

        assertEquals( "No password was provided.", exc1.getMessage() );
        assertEquals( "Empty password was provided.", exc2.getMessage() );
        assertEquals( "Password cannot be more than 100 characters.", exc3.getMessage() );

    }

    @Test
    @Transactional
    public void testRole() {
        User u1 = new User();
        u1.setUsername("testuser1");
        u1.setPassword("testpw1");
        u1.setRole("USER");

        assertEquals("testuser1", u1.getUsername());
        assertEquals("testpw1", u1.getPassword());
        assertEquals("USER", u1.getRole());

        u1.setRole("changedRole");

        assertEquals("testuser1", u1.getUsername());
        assertEquals("testpw1", u1.getPassword());
        assertEquals("changedRole", u1.getRole());

        // Test password at max bound
        u1.setRole("hhhhhhhhhhhhhhhhhhhh");
        assertEquals(20, u1.getRole().length());

        // Testing minimum bound
        u1.setRole("H");
        assertEquals(1, u1.getRole().length());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( NullPointerException.class, () -> errorUser.setRole(null) );
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setRole(""));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setRole("hhhhhhhhhhhhhhhhhhhha"));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> errorUser.setRole("skhdfg123"));
        Exception exc5 = assertThrows( IllegalArgumentException.class, () -> errorUser.setRole("USE@R"));
        Exception exc6 = assertThrows( IllegalArgumentException.class, () -> errorUser.setRole("US?><:P"));

        assertEquals( "No role was provided.", exc1.getMessage() );
        assertEquals( "Empty role was provided.", exc2.getMessage() );
        assertEquals( "Role cannot be more than 20 characters.", exc3.getMessage() );
        assertEquals( "Role should only contain characters.", exc4.getMessage() );
        assertEquals( "Role should only contain characters.", exc5.getMessage() );
        assertEquals( "Role should only contain characters.", exc6.getMessage() );

    }

    @Test
    @Transactional
    public void testHeight() {
        User u1 = new User();

        // height, first and last name are optional fields for the user to provide
        u1.setHeight(174.25);
        assertEquals(174.25, u1.getHeight());

        // Inner boundary
        u1.setHeight(0);
        assertEquals(0, u1.getHeight());

        // Inner boundary
        u1.setHeight(245.0);
        assertEquals(245.0, u1.getHeight());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> errorUser.setHeight(-1));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setHeight(245.1));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setHeight(-100));

        assertEquals( "Height is outside of permitted range.", exc1.getMessage() );
        assertEquals( "Height is outside of permitted range.", exc2.getMessage() );
        assertEquals( "Height is outside of permitted range.", exc3.getMessage() );

    }

    @Test
    @Transactional
    public void testFirstName() {
        User u1 = new User();
        u1.setFirstName("First");
        assertEquals("First", u1.getFirstName());

        // Inner boundary
        u1.setFirstName("F");
        assertEquals("F", u1.getFirstName());

        // Inner boundary
        u1.setFirstName("HHHHHHHHHHHHHHHHHHHH");
        assertEquals("HHHHHHHHHHHHHHHHHHHH", u1.getFirstName());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> errorUser.setFirstName(null));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setFirstName(""));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setFirstName("HHHHHHHHHHHHHHHHHHHHA"));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> errorUser.setFirstName("s123hj3"));
        Exception exc5 = assertThrows( IllegalArgumentException.class, () -> errorUser.setFirstName("fh[.;;@?"));

        assertEquals( "First name was expected but there was none.", exc1.getMessage() );
        assertEquals( "First name was expected but there was none.", exc2.getMessage() );
        assertEquals( "Maximum character limit is 20 for first name.", exc3.getMessage() );
        assertEquals( "First name should only contain characters.", exc4.getMessage() );
        assertEquals( "First name should only contain characters.", exc5.getMessage() );

    }

    @Test
    @Transactional
    public void testLastName() {
        User u1 = new User();
        u1.setLastName("Last");
        assertEquals("Last", u1.getLastName());

        // Inner boundary
        u1.setLastName("L");
        assertEquals("L", u1.getLastName());

        // Inner boundary
        u1.setLastName("HHHHHHHHHHHHHHHHHHHH");
        assertEquals("HHHHHHHHHHHHHHHHHHHH", u1.getLastName());

        // Error testing
        User errorUser = new User();
        Exception exc1 = assertThrows( IllegalArgumentException.class, () -> errorUser.setLastName(null));
        Exception exc2 = assertThrows( IllegalArgumentException.class, () -> errorUser.setLastName(""));
        Exception exc3 = assertThrows( IllegalArgumentException.class, () -> errorUser.setLastName("HHHHHHHHHHHHHHHHHHHHA"));
        Exception exc4 = assertThrows( IllegalArgumentException.class, () -> errorUser.setLastName("s123hj3"));
        Exception exc5 = assertThrows( IllegalArgumentException.class, () -> errorUser.setLastName("fh[.;;@?"));

        assertEquals( "Last name was expected but there was none.", exc1.getMessage() );
        assertEquals( "Last name was expected but there was none.", exc2.getMessage() );
        assertEquals( "Maximum character limit is 20 for last name.", exc3.getMessage() );
        assertEquals( "Last name should only contain characters.", exc4.getMessage() );
        assertEquals( "Last name should only contain characters.", exc5.getMessage() );

    }

}
