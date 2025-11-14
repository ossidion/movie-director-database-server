package com.example.accessing_data_jpa.controllers;

import com.example.accessing_data_jpa.entities.Movie;
import com.example.accessing_data_jpa.entities.MovieDirector;
import com.example.accessing_data_jpa.repositories.MovieDirectorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final MovieDirectorRepository movieDirectorRepository;

    // Spring injects MovieDirectorRepository so the controller can access the DB.
    public DirectorController(MovieDirectorRepository movieDirectorRepository) {
        this.movieDirectorRepository = movieDirectorRepository;
    }

    // Finds all MovieDirector entities where the director’s primaryName contains name (case-insensitive). Example: "spiel" will match "Spielberg".
    @GetMapping("/{name}")
    public List<Movie> getMoviesByDirectorName(@PathVariable String name) {
                // start fetch chain.
        return movieDirectorRepository
                 .findByDirector_PrimaryNameContainingIgnoreCase(name)
                .stream()
                .map(MovieDirector::getMovie)  // map MovieDirector to Movies
                .toList();

        // findByDirector_PrimaryNameContainingIgnoreCase(name)
        // findBy - SELECT query

        //Director - join the MovieDirector table with the Person table using the director_id foreign key

        //PrimaryName - filter by primary_name column in the Person table

        //ContainingIgnoreCase - LIKE %name% with case-insensitive comparison
    }
}
