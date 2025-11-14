package imdb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;
    private String primaryProfession;
    private String knownForTitles;

    protected Person() {}

    public Person(String id, String primaryName, Integer birthYear, Integer deathYear,
                  String primaryProfession, String knownForTitles) {
        this.id = id;
        this.primaryName = primaryName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.primaryProfession = primaryProfession;
        this.knownForTitles = knownForTitles;
    }

    @Override
    public String toString() {
        return String.format("Person[id=%s, name='%s', birthYear=%s, deathYear=%s, professions='%s', knownFor='%s']",
                id, primaryName, birthYear, deathYear, primaryProfession, knownForTitles);
    }

    public String getId() { return id; }
    public String getPrimaryName() { return primaryName; }
    public Integer getBirthYear() { return birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public String getPrimaryProfession() { return primaryProfession; }
    public String getKnownForTitles() { return knownForTitles; }
}
