package pl.edu.pja.tpo_12.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    private String address;
    @Setter
    private String country;

    @Getter
    @Setter
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    public Publisher() {}

    public Publisher(String name, String address, String country) {
        this.name = name;
        this.address = address;
        this.country = country;
    }
}