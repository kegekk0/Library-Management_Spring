package pl.edu.pja.tpo_12.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pja.tpo_12.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByPublisherId(Long publisherId);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.name = :genreName")
    List<Book> findByGenreName(@Param("genreName") String genreName);

    @Query("SELECT b FROM Book b WHERE b.publicationYear = :year")
    List<Book> findByPublicationYear(@Param("year") int year);
}
