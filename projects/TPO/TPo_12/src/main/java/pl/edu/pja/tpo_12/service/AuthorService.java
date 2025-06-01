package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
    List<Author> searchAuthors(String query);
}