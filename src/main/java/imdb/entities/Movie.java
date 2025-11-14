package imdb.entities;

import jakarta.persistence.*;

//   Model
// - defines what data looks like.
// - Maps directly to a DB table.
// - Each instance of Movie is a row in table.
@Entity
@Table(name = "movies")

public class Movie {
    @Id // Hibernate and repositories will look for this.
    @Column(name = "id", nullable = false)
    private String id;
    private int ordering;
    private String title;
    private String region;
    private String language;
    private String types;
    private String attributes;
    private int isOriginalTitle;

    protected Movie() {} // No args constructor for Hibernate to use Reflection in creating objects loaded from the database.

    public Movie(String id, int ordering, String title, String region, String language, String types, String attributes, int isOriginalTitle) {
        this.id = id;
        this.ordering = ordering;
        this.title = title;
        this.region = region;
        this.language = language;
        this.types = types;
        this.attributes = attributes;
        this.isOriginalTitle = isOriginalTitle;
    }

    @Override
    public String toString() {
        return String.format(
                "Movie[id=%s, ordering=%d, title='%s', region='%s',  language='%s', types='%s', attributes='%s', isOriginalTitle='%d']",
                id, ordering, title, region, language, types, attributes, isOriginalTitle);
    }

    // JPA via Hibernate needs these methods.

    public String getID() {return id;}

    public int getOrdering() {return ordering;}

    public String getTitle() {return title;}

    public String getRegion() {return region;}

    public String getLanguage() {return language;}

    public String getTypes() {return types;}

    public String getAttributes() {return attributes;}

    public int getIsOriginalTitle() {return isOriginalTitle;}

    public void setId(String id) {
        this.id = id;
    }
}
