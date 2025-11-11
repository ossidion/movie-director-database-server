package com.example.accessing_data_jpa;

import com.example.accessing_data_jpa.services.ReadData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Console-entry point version of app (no web interface yet).

// NOTE on CONTROLLER - can do this later for web interface.
// @Controller = for HTML views (Thymeleaf, etc.).
//@RestController = for REST APIs (return JSON).

@SpringBootApplication
public class AccessingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner imdb(ReadData readData) {
		return (args) -> {
			readData.loadDataFromFile();
		};
	}
}
