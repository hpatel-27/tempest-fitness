package com.hpatel.Tempest_Fitness.controllers;


import com.hpatel.Tempest_Fitness.TestConfig;
import com.hpatel.Tempest_Fitness.common.TestUtils;
import com.hpatel.Tempest_Fitness.models.User;
import com.hpatel.Tempest_Fitness.models.Weight;
import com.hpatel.Tempest_Fitness.services.UserService;
import com.hpatel.Tempest_Fitness.services.WeightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest( classes = TestConfig.class )
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class APIWeightTest {
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

    @Autowired
    private UserService userService;

    @Autowired
    private WeightService weightService;

    private User user;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
        SecurityContextHolder.clearContext();

        userService.deleteAll();
        weightService.deleteAll();

        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass123");
        user.setRole("TEST");
        userService.save(user);


        // Manually set authentication in SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                user, null, List.of(new SimpleGrantedAuthority("ROLE_TEST"))
        ));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @Transactional
    public void testAddWeight_WeightAlreadyExists() throws Exception {
        Weight weight = new Weight("2025-03-17", 175.5, user);
        weightService.save(weight);

        mvc.perform(post("/api/v1/weights").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(weight)))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"status\":\"failed\",\"message\":\"Weight with the date 2025-03-17 already exists.\"}"));

    }

    @Test
    @Transactional
    public void testAddWeight_UserNotFound() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                "fakeUser", null, List.of(new SimpleGrantedAuthority("ROLE_TEST"))
        ));
        SecurityContextHolder.setContext(securityContext);

        User fakeUser = new User();
        fakeUser.setUsername("fakeUser");
        fakeUser.setPassword("fakePass");
        fakeUser.setRole("TEST");

        Weight weight = new Weight("2025-03-17", 175.5, fakeUser);

        mvc.perform(post("/api/v1/weights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(weight)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"failed\",\"message\":\"User not found.\"}"));
    }

    @Test
    @Transactional
    public void testAddWeight_Success() throws Exception {
        Weight weight = new Weight("2025-03-17", 175.5, user);
        mvc.perform(post("/api/v1/weights").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(weight)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\",\"message\":\"Weight on 2025-03-17 successfully created.\"}"));
    }

    @Test
    @Transactional
    public void testDeleteWeight() throws Exception {

    }

    @Test
    @Transactional
    public void testEditWeight() throws Exception {

    }

    @Test
    @Transactional
    public void testGetWeights_Success() throws Exception {

    }

    @Test
    @Transactional
    public void testGetWeights_UserNotFound() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                "fakeUser", null, List.of(new SimpleGrantedAuthority("ROLE_TEST"))
        ));
        SecurityContextHolder.setContext(securityContext);

        User fakeUser = new User();
        fakeUser.setUsername("fakeUser");
        fakeUser.setPassword("fakePass");
        fakeUser.setRole("TEST");

        mvc.perform(get("/api/v1/weights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"failed\",\"message\":\"User not found.\"}"));

    }
}
