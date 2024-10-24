package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.SearchParams;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.director.DirectorStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.*;

@Slf4j
@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private static final String tableName = "films";
    private static final String fields = "id, title, description, release_date, duration, rating_id";
    private static final String FIND_ALL_QUERY = "SELECT " + fields + " from " + tableName;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String FIND_TOP_RATED_QUERY = "SELECT " + fields + ", COUNT(fl.user_id) AS count " +
            "FROM films " +
            "LEFT JOIN film_likes fl ON (id = fl.film_id) " +
            "GROUP BY " + fields + " ORDER BY count DESC LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO " + tableName +
            " (title, description, release_date, duration, rating_id) " +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE " + tableName + " SET " +
            "title = ?, description = ?, release_date = ?, duration = ?, rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM " + tableName + " WHERE id = ?";

    private static final String INSERT_LIKES_QUERY = "INSERT INTO film_likes" +
            " (film_id, user_id) VALUES (?, ?)";
    private static final String REMOVE_LIKES_QUERY = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
    private static final String FIND_LIKES_QUERY = "SELECT user_id FROM film_likes WHERE film_id = ? ORDER BY user_id";
    private static final String DELETE_LIKES_QUERY = "DELETE FROM film_likes WHERE film_id = ?";
    private static final String GET_SORTED_FILMS_BY_YEAR = "SELECT * FROM FILMS f " +
            "WHERE id IN (SELECT film_id FROM FILMS_DIRECTORS fd WHERE director_id = ?) " +
            "ORDER BY EXTRACT (YEAR FROM release_date);";
    private static final String GET_SORTED_FILMS_BY_LIKES = "SELECT * FROM FILMS f WHERE id IN " +
            "( SELECT fd.film_id " +
            "FROM films_directors fd " +
            "LEFT JOIN film_likes fl ON fd.film_id = fl.film_id " +
            "WHERE fd.director_id = ? " +
            "GROUP BY fd.film_id " +
            "ORDER BY COUNT(fl.user_id) DESC)";

    private static final String FIND_FILMS_BY_USER_LIKES_QUERY = "SELECT fl.film_id FROM film_likes AS fl " +
            "JOIN (SELECT film_id, COUNT(user_id) AS cou FROM film_likes GROUP BY film_id) AS gro ON fl.film_id = gro.film_id " +
            "WHERE fl.user_id = ? ORDER BY gro.cou DESC";

    private static final String FIND_ALL_BY_NAME_CONTEXT = "SELECT id FROM " + tableName +
            " WHERE LOCATE(?, title) > 0 ";
    private static final String FIND_ALL_BY_DIRECTOR_CONTEXT = "SELECT id FROM " + tableName +
            " WHERE films.id IN " + "(SELECT fd.film_id FROM films_directors AS fd WHERE fd.director_id IN " +
            "(SELECT d.id FROM directors AS d WHERE LOCATE(?, d.name) > 0)) ";


    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;
    private final DirectorStorage directorStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate,
                         RowMapper<Film> mapper,
                         GenreStorage genreStorage,
                         RatingStorage ratingStorage,
                         DirectorStorage directorStorage) {
        super(jdbcTemplate, mapper);
        this.genreStorage = genreStorage;
        this.ratingStorage = ratingStorage;
        this.directorStorage = directorStorage;
    }

    @Override
    public Film addElement(Film film) {
        Integer newId = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate().format(DATE_FORMATTER),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(newId);
        film.setMpa(ratingStorage.getElement(film.getMpa().getId()));

        addFilmAndGenres(film);

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
                newFilm.getMpa().getId(),
                newFilm.getId());

        deleteFilmLikesAndGenres(newFilm.getId());
        addFilmAndGenres(newFilm);
        newFilm.setMpa(ratingStorage.getElement(newFilm.getMpa().getId()));

        return newFilm;
    }

    @Override
    public List<Film> getTopRatedFilms(int count) {
        return findMany(FIND_TOP_RATED_QUERY, count);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        update(INSERT_LIKES_QUERY, filmId, userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        update(REMOVE_LIKES_QUERY, filmId, userId);
    }

    public List<Integer> getFilmLikes(Integer filmId) {
        return retrieveIdList(FIND_LIKES_QUERY, filmId);
    }

    @Override
    public List<Film> getAllElements() {
        List<Film> baseList = findMany(FIND_ALL_QUERY);
        baseList.forEach(this::setFilmMpaAndGenresAndDirectors);
        return baseList;
    }

    @Override
    public Film getElement(Integer id) {
        Film film = findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Не найден фильм с id = " + id));
        setFilmMpaAndGenresAndDirectors(film);
        return film;
    }

    @Override
    public List<Film> getSortedFilmsByYear(Integer id) {
        List<Film> films = findMany(GET_SORTED_FILMS_BY_YEAR, id);
        films.forEach(this::setFilmMpaAndGenresAndDirectors);
        return films;
    }

    @Override
    public List<Film> getSortedFilmsByLikes(Integer id) {
        List<Film> films = findMany(GET_SORTED_FILMS_BY_LIKES, id);
        films.forEach(this::setFilmMpaAndGenresAndDirectors);
        return films;
    }

    // заполняет коллекции жанров в фильме по данным из БД
    private void setFilmMpaAndGenresAndDirectors(Film film) {
        film.setMpa(ratingStorage.getElement(film.getMpa().getId()));
        film.getGenres().addAll(genreStorage.getFilmGenresById(film.getId()));
        film.getDirectors().addAll(directorStorage.getDirectorsByFilmId(film.getId()));
    }

    //добавляет жанры фильма в БД
    private void addFilmAndGenres(Film film) {
        genreStorage.updateFilmGenresById(film.getId(), film.getGenres());
    }

    //удаляет все лайки и жанры по id фильма в БД
    private void deleteFilmLikesAndGenres(Integer filmId) {
        genreStorage.deleteFilmGenresById(filmId);
        delete(DELETE_LIKES_QUERY, filmId);
    }

    @Override
    public List<Film> getFilmsLikesByUsers(Integer userId, Integer friendId) {
        List<Integer> filmsIds = retrieveIdList(FIND_FILMS_BY_USER_LIKES_QUERY, userId);
        filmsIds.retainAll(retrieveIdList(FIND_FILMS_BY_USER_LIKES_QUERY, friendId)); //оставляем в filmsIds общие id фильмов
        return filmsIds.stream().map(this::getElement).toList();
    }

    public List<Film> getByContext(SearchParams searchParams) {
        Set<Integer> filmIds = new LinkedHashSet<>();
        log.info("SearchParams {}", searchParams);
        if (searchParams.isNeedTitle()) {
            filmIds.addAll(retrieveIdList(FIND_ALL_BY_NAME_CONTEXT, searchParams.getQuery()));
        }
        if (searchParams.isNeedDirector()) {
            filmIds.addAll(retrieveIdList(FIND_ALL_BY_DIRECTOR_CONTEXT, searchParams.getQuery()));
        }
        List<Film> baseList = filmIds.stream()
                .map(this::getElement)
                .toList();
        baseList.forEach(this::setFilmMpaAndGenresAndDirectors);
        return baseList;
    }

}
