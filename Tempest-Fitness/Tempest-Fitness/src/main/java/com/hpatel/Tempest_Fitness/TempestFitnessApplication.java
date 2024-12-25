package com.hpatel.Tempest_Fitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hpatel.Tempest_Fitness.repositories")
@EntityScan(basePackages = "com.hpatel.Tempest_Fitness.models")
public class TempestFitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(TempestFitnessApplication.class, args);
	}

}
