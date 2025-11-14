package com.example.accessing_data_jpa.entities;

import jakarta.persistence.*;

// Many to many 'Movie / Director' relationship.

@Entity
@Table(name = "movie_director")
public class MovieDirector {

    @Id // BuildMovieDirectorTable (@Service) generates a universally unique identifier.
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Person director;

    public MovieDirector() {}

    // Constructor for easier creation
    public MovieDirector(String id, Movie movie, Person director) {
        this.id = id;
        this.movie = movie;
        this.director = director;
    }

    // Getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id; // Only set before persisting
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }
}
