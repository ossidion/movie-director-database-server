package com.example.accessing_data_jpa.repositories;

import com.example.accessing_data_jpa.entities.Crew;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.stream.Stream;

public interface CrewRepository extends CrudRepository<Crew, String> {

    // BuildMovieDirectorTable reads a lot of data so a Stream allows for lazy loading from the database i.e. reading large data without loading it all at once.

    // CRUD is minimal and doesn't add extra methods which may trigger full loads into memory.

    @Query("SELECT c FROM Crew c") // Custom query (JPQL)
    Stream<Crew> streamAllCrew();
}
