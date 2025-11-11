package com.example.accessing_data_jpa.repositories;

import com.example.accessing_data_jpa.entities.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// The Repository connects the Model to the database - it's the bridge between data and logic.

public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

//    Movie findById(Long id);
}
