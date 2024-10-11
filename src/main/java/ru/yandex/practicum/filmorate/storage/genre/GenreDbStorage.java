package ru.yandex.practicum.filmorate.storage.genre;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {

    private static final String INSERT_FILM_GENRES_QUERY = "INSERT INTO film_genres" +
            " (film_id, genre_id) VALUES (?, ?)";
    private static final String FIND_FILM_GENRES_QUERY = """
        SELECT g.id,
            g.name,
            g.description
        FROM film_genres
        LEFT JOIN genres g ON genre_id = g.id
        WHERE film_id = ?
        ORDER BY g.id
        """;
    private static final String DELETE_FILM_GENRES_QUERY = "DELETE FROM film_genres WHERE film_id = ?";

    private static final String FIND_GENRE_QUERY = "SELECT id, name, description FROM genres WHERE id = ?";

    public GenreDbStorage (JdbcTemplate jdbcTemplate, RowMapper<Genre> mapper) {
        super(jdbcTemplate, mapper);
    }

    public Set<Genre> getFilmGenresById(Integer filmId) {
        return new HashSet<Genre>(findMany(FIND_FILM_GENRES_QUERY, filmId));
    }

    @Override
    public boolean isValidGenreId(Integer id) {
        if (id ==null) return false;
        return findOne(FIND_GENRE_QUERY, id).isPresent();
    }

    @Override
    public void deleteFilmGenresById(Integer filmId) {
        delete(DELETE_FILM_GENRES_QUERY, filmId);
    }

    @Override
    public void updateFilmGenresById(Integer filmId, Set<Genre> genreList) {
        if (genreList != null) {
            genreList.forEach(genre -> update(INSERT_FILM_GENRES_QUERY, filmId, genre.getId()));
        }
    }

    @Override
    public Genre addElement(Genre element) {
        return null;
    }

    @Override
    public boolean deleteElement(Genre element) {
        return false;
    }

    @Override
    public Genre updateElement(Genre newElement) {
        return null;
    }

    @Override
    public List<Genre> getAllElements() {
        return null;
    }

    @Override
    public Genre getElement(Integer id) {
        return null;
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return false;
    }
}
