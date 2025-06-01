package pl.edu.pja.tpo_12.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }
}