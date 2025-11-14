package imdb.repositories;

import imdb.entities.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieDirectorRepository extends JpaRepository<MovieDirector, String> {

    // Spring Data JPA will generate the query automatically
    List<MovieDirector> findByDirector_PrimaryNameContainingIgnoreCase(String name);

// join a person with movie_director

}
