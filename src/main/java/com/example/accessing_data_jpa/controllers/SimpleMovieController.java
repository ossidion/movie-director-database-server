package com.example.accessing_data_jpa.controllers;

import com.example.accessing_data_jpa.entities.Movie;
import com.example.accessing_data_jpa.repositories.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SimpleMovieController {

    private final MovieRepository movieRepository;

    public SimpleMovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Return first 50 movies to keep JSON manageable
    @GetMapping("/api/movies")
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movies::add);
        return movies.stream().limit(50).toList();
    }

}
