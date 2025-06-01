package pl.edu.pja.tpo_12.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pja.tpo_12.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByLastName(String lastName);
    List<Author> findByFirstNameContainingIgnoreCase(String firstName);
    List<Author> findByLastNameContainingIgnoreCase(String lastName);
}