package com.example.accessing_data_jpa.services;

import com.example.accessing_data_jpa.entities.Movie;
import com.example.accessing_data_jpa.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

// Handles Model logic (not just store it like the @Entity)

@Service
public class ReadData implements MovieRepository{

    private final MovieRepository repository;

    public ReadData(MovieRepository repository) {
        this.repository = repository;
    }

    public void loadDataFromFile() {
        String filePath = "src/main/resources/title.akas.tsv.gz";

        List<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))))) {

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                // IMDb title.akas.tsv columns:
                // titleId, ordering, title, region, language, types, attributes, isOriginalTitle
                // e.g. tt0000001	1	Carmencita	US	en	\N	\N
                if (parts.length >= 8 && Objects.equals(parts[3], "US")) {
                    String id = parts[0];
                    int ordering = parseIntSafe(parts[1]);
                    String title = parts[2];
                    String region = parts[3];
                    String language = parts[4];
                    String types = parts[5];
                    String attributes = parts[6];
                    int isOriginalTitle = parseIntSafe(parts[7]);

                    movies.add(new Movie(id, ordering, title, region, language, types, attributes, isOriginalTitle));
                }
            }

            repository.saveAll(movies);
            System.out.println("Loaded " + movies.size() + " US-region movies into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return List.of();
    }

    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Movie> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Movie> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Movie> findAll() {
        return null;
    }

    @Override
    public Iterable<Movie> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Movie entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Movie> entities) {

    }

    @Override
    public void deleteAll() {

    }
}