package com.hpatel.Tempest_Fitness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures the SpringBootApplication to accept cross-origin
 * requests
 *
 * @author Harsh Patel
 */
@Configuration
public class CorsConfig {

    /**
     * Adds the CORS mapping to allow requests from localhost:3000 with the path /api/v1/...,
     * so any request that starts with /api/v1/
     * @return The WebMvcConfigurer to allow the cross-origin requests from the specified routes
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
