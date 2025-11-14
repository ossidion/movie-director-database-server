package imdb;

import imdb.services.ReadCrewData;
import imdb.services.ReadMovieData;
import imdb.services.ReadPersonData;
import imdb.services.BuildMovieDirectorTable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("imdb/entities")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
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
