package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private static final String fields = "id, title, description, release_date, duration, rating_id";
    private static final String tableName = "films";
    private static final String FIND_ALL_QUERY = "SELECT " + fields + " from " + tableName;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO " + tableName +
            " (title, description, release_date, duration, rating_id) " +
            " VALUES (?, ?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE " + tableName + " SET " +
            "title = ?, description = ?, release_date = ?, duration = ?, rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM " + tableName + " WHERE id = ?";

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FilmDbStorage (JdbcTemplate jdbcTemplate, RowMapper<Film> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    public Film addElement(Film film) {

        Integer newId = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate().format( DATE_FORMATTER),
                film.getDuration(),
                film.getRating()
        );
        film.setId(newId);

        //film.getGenres()

        return film;
    }

    @Override
    public boolean deleteElement(Film film) {
        return delete(DELETE_QUERY, film.getId());
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return delete(DELETE_QUERY, id);
    }

    @Override
    public Film updateElement(Film newFilm) {
        update(UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate().format(DATE_FORMATTER),
                newFilm.getDuration(),
                newFilm.getRating(),
                newFilm.getId());
        return newFilm;
    }

    @Override
    public List<Film> getAllElements() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Film getElement(Integer id) {
        return findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Не найден фильм с id = " + id));
    }
}
