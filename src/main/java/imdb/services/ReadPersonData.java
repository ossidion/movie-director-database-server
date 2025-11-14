package imdb.services;

import imdb.entities.Person;
import imdb.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class ReadPersonData {

    private final PersonRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ReadPersonData(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void loadDataFromFile() {
        String filePath = "src/main/resources/name.basics.tsv.gz";
        List<Person> batch = new ArrayList<>();
        int batchSize = 1000;
        int count = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))))) {

            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String name = parts[1];
                    Integer birthYear = parseIntSafe(parts[2]);
                    Integer deathYear = parseIntSafe(parts[3]);
                    String primaryProfession = parts[4];
                    String knownForTitles = parts[5];

                    batch.add(new Person(id, name, birthYear, deathYear, primaryProfession, knownForTitles));
                    count++;

                    if (batch.size() >= batchSize) {
                        repository.saveAll(batch);
                        entityManager.flush();
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

            System.out.println("Loaded " + count + " people into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Null because birthYear or deathYear might be missing (\N in the TSV). 0 not a valid year.
    private Integer parseIntSafe(String value) {
        try {
            return (value != null && !value.equals("\\N")) ? Integer.parseInt(value) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
