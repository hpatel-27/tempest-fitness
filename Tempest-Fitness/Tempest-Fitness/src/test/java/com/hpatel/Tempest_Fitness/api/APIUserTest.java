package com.hpatel.Tempest_Fitness.api;

import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.common.TestUtils;
import com.hpatel.Tempest_Fitness.dto.LoginRequest;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration
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
                        .andExpect(content().string("User registered successfully!"));

        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(u2)))
                        .andExpect(status().isConflict())
                        .andExpect(content().string("Username already exists."));

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
                .andExpect(content().string("User registered successfully!"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("TestUsername");
        loginRequest.setPassword("TestPassword");

        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(loginRequest)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Login successful!"));


        LoginRequest badRequest = new LoginRequest();
        badRequest.setUsername("BadUsername");
        badRequest.setPassword("BadPassword");

        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(badRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));

    }
}
