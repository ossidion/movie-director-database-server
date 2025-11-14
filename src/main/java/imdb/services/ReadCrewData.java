package imdb.services;

import imdb.entities.Crew;
import imdb.repositories.CrewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class ReadCrewData {

    private final CrewRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ReadCrewData(CrewRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void loadDataFromFile() {
        String filePath = "src/main/resources/title.crew.tsv.gz";
        List<Crew> batch = new ArrayList<>();
        int batchSize = 1000; // adjust as needed
        int count = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))))) {

            // Skip header
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length >= 3) {
                    String id = parts[0];
                    String directors = parts[1];
                    String writers = parts[2];

                    batch.add(new Crew(id, directors, writers));
                    count++;

                    if (batch.size() >= batchSize) {
                        repository.saveAll(batch);
                        entityManager.flush(); // write to DB immediately
                        entityManager.clear(); // free memory
                        batch.clear();
                    }
                }
            }

            if (!batch.isEmpty()) {
                repository.saveAll(batch);
                entityManager.flush();
                entityManager.clear();
            }

            System.out.println("Loaded " + count + " crew entries into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
