package com.example.accessing_data_jpa;

import com.example.accessing_data_jpa.services.ReadCrewData;
import com.example.accessing_data_jpa.services.ReadMovieData;
import com.example.accessing_data_jpa.services.ReadPersonData;
import com.example.accessing_data_jpa.services.BuildMovieDirectorTable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("com.example.accessing_data_jpa.entities")
public class AccessingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class, args);
	}

	// The Application Context manages beans i.e. @Services, @Components, etc.

	// @Bean - it runs automatically after the application context has started and right before the app is ready to serve http requests.
	@Bean
	public CommandLineRunner imdb(ReadMovieData readData,
								  ReadCrewData crewData,
								  ReadPersonData personData,
								  BuildMovieDirectorTable buildMovieDirectorTable) {
		boolean loadData = false;
		return (args) -> {
			if (loadData) {
				readData.loadDataFromFile();
				crewData.loadDataFromFile();
				personData.loadDataFromFile();
				buildMovieDirectorTable.buildLinks();
			}

		};
	}
}


// Return a lambda returning a new CommandLineRunner with the run method executes @Services (provides the code to run after Spring finishes initialization).
