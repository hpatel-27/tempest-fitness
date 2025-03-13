package com.hpatel.Tempest_Fitness;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan( { "com.hpatel.Tempest_Fitness" } )
@ActiveProfiles("test")
public class TestConfig {
}
