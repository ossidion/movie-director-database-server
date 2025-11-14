package imdb.services;

import imdb.entities.Crew;
import imdb.entities.MovieDirector;
import imdb.entities.Movie;
import imdb.entities.Person;
import imdb.repositories.MovieDirectorRepository;
import imdb.repositories.MovieRepository;
import imdb.repositories.PersonRepository;
import imdb.repositories.CrewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class BuildMovieDirectorTable {

    private final CrewRepository crewRepository;
    private final MovieRepository moviesRepository;
    private final PersonRepository personRepository;
    private final MovieDirectorRepository movieDirectorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BuildMovieDirectorTable(CrewRepository crewRepository,
                                   MovieRepository moviesRepository,
                                   PersonRepository personRepository,
                                   MovieDirectorRepository movieDirectorRepository) {
        this.crewRepository = crewRepository;
        this.moviesRepository = moviesRepository;
        this.personRepository = personRepository;
        this.movieDirectorRepository = movieDirectorRepository;
    }

    @Transactional
    public void buildLinks() {
        int batchSize = 50;
        int count = 0;

        // Streaming allows for reading in a lazy loading way.
        // streamAllCrew() - @Query("SELECT c FROM Crew c")
        try (Stream<Crew> crewStream = crewRepository.streamAllCrew()) {

            // crewStream::iterator - iterator() method of the stream.
            // (Iterable<Crew>) casting method reference to an Iterable<Crew> so it can be used in a for-each loop.
            for (Crew crew : (Iterable<Crew>) crewStream::iterator) {
                String movieId = crew.getId();

                // Optional container, may or may not hold a value in case DB lookup returns null (no value).
                // findById() - JPA Repository!
                Optional<Movie> movieOpt = moviesRepository.findById(movieId);
                // Continue - guarantees that the Optional contains a value or will throw exception at movieOpt.get();
                if (movieOpt.isEmpty()) continue;
                Movie movie = movieOpt.get();

                // filter Crew with no directors.
                String directorsStr = crew.getDirectors();
                if (directorsStr == null || directorsStr.isEmpty()) continue;

                String[] directorIds = directorsStr.split(",");

                // Map director array against People entities
                for (String directorId : directorIds) {
                    Optional<Person> personOpt = personRepository.findById(directorId.trim());
                    if (personOpt.isEmpty()) continue;

                    // Many-to-many relationship between movies and directors.
                    Person director = personOpt.get();

                    // Save Person object (entity) along with a Movie object to create MovieDirector.
                    //UUID 128-bit unique identifier.
                    MovieDirector md = new MovieDirector(UUID.randomUUID().toString(), movie, director);
                    movieDirectorRepository.save(md);
                }

                // Flush and clear periodically to avoid memory issues
                // Count increments for each MovieDirector processed.
                // When count is a multiple of batchSize (e.g., every 50 records), the block runs.
                if (++count % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        }

        System.out.println("MovieDirector table populated successfully!");
    }
}
