package com.hpatel.Tempest_Fitness;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@EnableAutoConfiguration
@SpringBootTest( classes = TestConfig.class )
@ActiveProfiles("test")  // Activate the test profile for the H2 database
public class DBInteractionTest {
}
