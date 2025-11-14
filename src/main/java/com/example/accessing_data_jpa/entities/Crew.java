package com.example.accessing_data_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "crew")
public class Crew {
    @Id
    @Column(name = "movie_id", nullable = false)
    private String movieId; // same IDs as Movie.id
    private String directors;
    private String writers;

    protected Crew() {}

    public Crew(String movieId, String directors, String writers) {
        this.movieId = movieId;
        this.directors = directors;
        this.writers = writers;
    }

    @Override
    public String toString() {
        return String.format(
                "Crew[movieId='%s', directors='%s', writers='%s']",
                movieId, directors, writers
        );
    }

    public String getId() {
        return movieId;
    }

    public String getDirectors() {
        return directors;
    }

    public String getWriters() {
        return writers;
    }
}

