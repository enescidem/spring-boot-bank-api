package com.enescidem.starter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.enescidem"})
@EnableJpaRepositories(basePackages = {"com.enescidem"})
@ComponentScan(basePackages = {"com.enescidem"})
@SpringBootApplication
public class ObssBankDemoStarter {

	public static void main(String[] args) {
		SpringApplication.run(ObssBankDemoStarter.class, args);
	}

}
