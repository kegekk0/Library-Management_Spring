package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    List<Book> searchBooks(String query);
    List<Book> getBooksByAuthor(Long authorId);
    List<Book> getBooksByPublisher(Long publisherId);
    List<Book> getBooksByGenre(String genreName);
    List<Book> getBooksByPublicationYear(int year);
}