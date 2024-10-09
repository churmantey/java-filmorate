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

    private static final String INSERT_LIKES_QUERY = "INSERT INTO film_likes" +
            " (film_id, user_id) VALUES (?, ?)";
    private static final String FIND_LIKES_QUERY = "SELECT user_id FROM film_likes WHERE film_id = ?";
    private static final String DELETE_LIKES_QUERY = "DELETE FROM film_likes WHERE film_id = ?";
    private static final String INSERT_GENRES_QUERY = "INSERT INTO film_genres" +
            " (film_id, genre_id) VALUES (?, ?)";
    private static final String FIND_GENRES_QUERY = "SELECT genre_id FROM film_genres WHERE film_id = ?";
    private static final String DELETE_GENRES_QUERY = "DELETE FROM film_genres WHERE film_id = ?";

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
                film.getMpa()
        );
        film.setId(newId);

        addFilmLikesAndGenres(film);

        return film;
    }

    @Override
    public boolean deleteElement(Film film) {
        deleteFilmLikesAndGenres(film.getId());
        return delete(DELETE_QUERY, film.getId());
    }

    @Override
    public boolean deleteElementById(Integer id) {
        deleteFilmLikesAndGenres(id);
        return delete(DELETE_QUERY, id);
    }

    @Override
    public Film updateElement(Film newFilm) {
        update(UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate().format(DATE_FORMATTER),
                newFilm.getDuration(),
                newFilm.getMpa(),
                newFilm.getId());

        deleteFilmLikesAndGenres(newFilm.getId());
        addFilmLikesAndGenres(newFilm);

        return newFilm;
    }

    @Override
    public List<Film> getAllElements() {
        List<Film> baseList = findMany(FIND_ALL_QUERY);
        baseList.forEach(this::setFilmLikesAndGenres);
        return baseList;
    }

    @Override
    public Film getElement(Integer id) {
        Film film = findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Не найден фильм с id = " + id));
        setFilmLikesAndGenres(film);
        return film;
    }

    // заполняет коллекции жанров и лайков в фильме по данным из БД
    private void setFilmLikesAndGenres (Film film) {
        film.getLikes().addAll(retrieveIdList(FIND_LIKES_QUERY, film.getId()));
        film.getGenres().addAll(retrieveIdList(FIND_GENRES_QUERY, film.getId()));
    }

    //добавляет лайки и жанры фильма в БД
    private void addFilmLikesAndGenres(Film film) {
        if (!film.getLikes().isEmpty()) {
            film.getLikes().forEach(userId -> update(INSERT_LIKES_QUERY, film.getId(), userId));
        }
        if (!film.getGenres().isEmpty()) {
            film.getGenres().forEach(genreId -> update(INSERT_GENRES_QUERY, film.getId(), genreId));
        }
    }

    //удвляет все лайки и жанры по id фильма в БД
    private void deleteFilmLikesAndGenres(Integer filmId) {
        delete(DELETE_LIKES_QUERY, filmId);
        delete(DELETE_GENRES_QUERY, filmId);
    }
}
