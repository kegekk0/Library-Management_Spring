package pl.edu.pja.tpo_12.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String title;

    @Setter
    @Getter
    @Column(unique = true)
    private String isbn;

    @Getter
    @Setter
    private Integer publicationYear;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )

    private Set<Genre> genres = new HashSet<>();

    public Book() {}

    public Book(String title, String isbn, Integer publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }
}