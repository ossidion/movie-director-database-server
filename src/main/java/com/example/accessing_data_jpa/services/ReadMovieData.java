package com.example.accessing_data_jpa.services;

import com.example.accessing_data_jpa.entities.Movie;
import com.example.accessing_data_jpa.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

@Service
public class ReadMovieData {

    private final MovieRepository repository;

    // @PersistenceContext is a JPA annotation that tells Spring to inject an EntityManager into the class. It can be used for .flush() and .clear().
    @PersistenceContext
    private EntityManager entityManager;

    // Constructor based dependency injection.
    public ReadMovieData(MovieRepository repository) {
        this.repository = repository;
    }

    // Wrap whole batch load in single transaction and can use saveAll(). This ensures the whole batch succeeds or fails together (atomicity).
    @Transactional
    public void loadDataFromFile() {
        String filePath = "src/main/resources/title.akas.tsv.gz";
        List<Movie> batch = new ArrayList<>();
        int batchSize = 1000;
        int count = 0;

        // FileInputStream - reads bytes from the file.
        //GZIPInputStream - decompresses .gz.
        //InputStreamReader - converts bytes to characters.
        //BufferedReader - gives convenient readLine().
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))))) {

            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 8 && Objects.equals(parts[3], "US")) {
                    String id = parts[0];
                    int ordering = parseIntSafe(parts[1]);
                    String title = parts[2];
                    String region = parts[3];
                    String language = parts[4];
                    String types = parts[5];
                    String attributes = parts[6];
                    int isOriginalTitle = parseIntSafe(parts[7]);

                    batch.add(new Movie(id, ordering, title, region, language, types, attributes, isOriginalTitle));
                    count++;

                    if (batch.size() >= batchSize) {

                        // saveAll(batch) - add to persistence context memory
                        repository.saveAll(batch);

                        //  flush() write pending changes currently in the persistance context memory to database.
                        entityManager.flush();

                        // Clear persistence context.
                        entityManager.clear();
                        batch.clear();
                    }
                }
            }

            if (!batch.isEmpty()) {
                repository.saveAll(batch);
                entityManager.flush();
                entityManager.clear();
            }

            System.out.println("Loaded " + count + " US-region movies into database.");

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
}
