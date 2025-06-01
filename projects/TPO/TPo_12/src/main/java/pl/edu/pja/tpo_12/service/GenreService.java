package pl.edu.pja.tpo_12.service;

import pl.edu.pja.tpo_12.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    Genre getGenreById(Long id);
    Genre saveGenre(Genre genre);
    Genre updateGenre(Long id, Genre genre);
    void deleteGenre(Long id);
    Genre getGenreByName(String name);
}