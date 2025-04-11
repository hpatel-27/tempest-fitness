package com.hpatel.Tempest_Fitness.controllers;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.common.TestUtils;
import com.hpatel.Tempest_Fitness.dto.LoginRequestDTO;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest( classes = TestConfig.class )
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class APIUserTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc mvc;


    /**
     * The web application context
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * The user service
     */
    @Autowired
    private UserService service;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
        SecurityContextHolder.clearContext();
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testRegisterUser() throws Exception {
        final User u1 = new User();
        u1.setUsername("user");
        u1.setPassword("pass");
        u1.setRole("TEST");

        final User u2 = new User();
        u2.setUsername("user");
        u2.setPassword("pass2");
        u2.setRole("TEST");

        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u1)))
                        .andExpect(status().isCreated())
                        .andExpect(content().json("{\"status\":\"success\",\"message\":\"User registered successfully!\"}"));

        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u2)))
                        .andExpect(status().isConflict())
                        .andExpect(content().json("{\"status\":\"failed\",\"message\":\"Username already exists.\"}"));
    }

    @Test
    @Transactional
    public void testLoginUser() throws Exception {
        final User u1 = new User();
        u1.setUsername("TestUsername");
        u1.setPassword("TestPassword");
        u1.setRole("TEST");

        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u1)))
                        .andExpect(status().isCreated())
                        .andExpect(content().json("{\"status\":\"success\",\"message\":\"User registered successfully!\"}"));

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("TestUsername");
        loginRequestDTO.setPassword("TestPassword");

        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(loginRequestDTO)))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"status\":\"success\",\"message\":\"Login successful!\"}"));

        LoginRequestDTO badRequest = new LoginRequestDTO();
        badRequest.setUsername("BadUsername");
        badRequest.setPassword("BadPassword");

        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(badRequest)))
                        .andExpect(status().isUnauthorized())
                        .andExpect(content().json("{\"status\":\"failed\",\"message\":\"Invalid username or password.\"}"));
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        // There should be no users currently
        assertEquals(0, service.count());

        // First create a user
        final User u1 = new User();
        u1.setUsername("user");
        u1.setPassword("pass");
        u1.setRole("TEST");
        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u1)))
                        .andExpect(status().isCreated())
                        .andExpect(content().json("{\"status\":\"success\",\"message\":\"User registered successfully!\"}"));

        // Confirm registration
        assertEquals(1, service.count());

        // Register another user so that we can confirm only the requested user is deleted
        final User u2 = new User();
        u2.setUsername("user2");
        u2.setPassword("pass2");
        u2.setRole("TEST");
        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u2)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"User registered successfully!\"}"));

        // Confirm registration
        assertEquals(2, service.count());

        // Delete the first user
        mvc.perform(delete("/api/v1/users/user").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(u1)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"user was successfully deleted!\"}"));

        // Confirm deletion
        assertEquals(1, service.count());

        // Attempt to delete a user that does not exist (We can do user again to make sure it did
        // not persist
        mvc.perform(delete("/api/v1/users/user").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u1)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"failed\",\"message\":\"No user found with the name user.\"}"));

        // Confirm Not Found
        assertEquals(1, service.count());

        // Delete the second user
        mvc.perform(delete("/api/v1/users/user2").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u2)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"user2 was successfully deleted!\"}"));

        // Confirm deletion
        assertEquals(0, service.count());

    }

    @Test
    @Transactional
    public void testGetCurrentUser() throws Exception {
        // Register a user
        final User u1 = new User();
        u1.setUsername("testUser");
        u1.setPassword("testPass");
        u1.setRole("TEST");
        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u1)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"User registered successfully!\"}"));

        // Login a user
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUser");
        loginRequestDTO.setPassword("testPass");
        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"Login successful!\"}"));

        // Manually set authentication in the SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                u1, null, List.of(new SimpleGrantedAuthority("ROLE_TEST"))));
        SecurityContextHolder.setContext(securityContext);

        // Check that they are the current user
        mvc.perform(get("/api/v1/currentUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.asJsonString(u1)));

    }

    @Test
    @Transactional
    public void testGetCurrentUserNull() throws Exception {
        // Attempt to get the current user, even though there isn't one
        mvc.perform(get("/api/v1/currentUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"failed\",\"message\":\"Error finding the current user.\"}"));
    }

    @Test
    @Transactional
    public void testLogout_Successful() throws Exception {
        // Manually set an authenticated user in the SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                "testUser", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        SecurityContextHolder.setContext(securityContext);

        // Perform the logout request
        mvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"Logout successful!\"}"));
    }

    @Test
    @Transactional
    public void testLogout_WhenNoUserLoggedIn_ShouldReturnUnauthorized() throws Exception {
        // Ensure the SecurityContext is empty (simulating no user logged in)
        SecurityContextHolder.clearContext();

        mvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("No user is currently logged in.")));
    }

    @Test
    @Transactional
    public void testLogout_AnonymousUser_ShouldReturnUnauthorized() throws Exception {
        // Simulate an anonymous user
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new AnonymousAuthenticationToken(
                "key", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        SecurityContextHolder.setContext(securityContext);

        mvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("No user is currently logged in.")));
    }

}
