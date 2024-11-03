package ru.yandex.practicum.filmorate.storage.film;

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

import java.util.ArrayList;
import java.util.List;


@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private static final String tableName = "films";
    private static final String fields = "id, title, description, release_date, duration, rating_id";
    private static final String FIND_ALL_QUERY = "SELECT " + fields + " from " + tableName;
    private static final String FIND_BY_ID_QUERY = "SELECT " + fields + ", AVG(fl.rate) AS avg_rate " +
            "FROM " + tableName + " " + """
            LEFT JOIN film_likes fl ON (id = fl.film_id)
            WHERE id = ?
            GROUP BY
            """ + fields;

    private static final String FIND_TOP_RATED_QUERY = "SELECT " + fields + ", AVG(fl.rate) AS avg_rate " +
            "FROM films " +
            "LEFT JOIN film_likes fl ON (id = fl.film_id) " +
            "GROUP BY " + fields + " ORDER BY avg_rate DESC LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO " + tableName +
            " (title, description, release_date, duration, rating_id) " +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE " + tableName + " SET " +
            "title = ?, description = ?, release_date = ?, duration = ?, rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM " + tableName + " WHERE id = ?";

    private static final String INSERT_LIKES_QUERY = "INSERT INTO film_likes" +
            " (film_id, user_id, rate) VALUES (?, ?, ?)";
    private static final String REMOVE_LIKES_QUERY = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
    private static final String FIND_LIKES_QUERY = "SELECT user_id FROM film_likes WHERE film_id = ? ORDER BY user_id";
    private static final String DELETE_LIKES_QUERY = "DELETE FROM film_likes WHERE film_id = ?";
    private static final String GET_SORTED_FILMS_BY_YEAR = "SELECT * FROM FILMS f " +
            "WHERE id IN (SELECT film_id FROM FILMS_DIRECTORS fd WHERE director_id = ?) " +
            "ORDER BY EXTRACT (YEAR FROM release_date);";
    private static final String GET_SORTED_FILMS_BY_LIKES = "SELECT f.*, COUNT(fl.rate) AS likes_count " +
            "FROM films f " +
            "JOIN FILMS_DIRECTORS fd ON f.id=fd.film_id AND fd.DIRECTOR_ID = ? " +
            "LEFT JOIN film_likes fl ON f.id=fl.film_id " +
            "GROUP BY f.id " +
            "ORDER BY likes_count DESC";

    private static final String GET_SORTED_FILMS_BY_RATES = "SELECT f.*, AVG(fl.rate) AS avg_rate " +
            "FROM films f " +
            "JOIN FILMS_DIRECTORS fd ON f.id=fd.film_id AND fd.DIRECTOR_ID = ? " +
            "LEFT JOIN film_likes fl ON f.id=fl.film_id " +
            "GROUP BY f.id " +
            "ORDER BY avg_rate DESC";

    private static final String FIND_FILMS_BY_USER_LIKES_QUERY = "SELECT fl.film_id FROM film_likes AS fl " +
            "JOIN (SELECT film_id, AVG(rate) AS avg_rate FROM film_likes GROUP BY film_id) AS gro ON fl.film_id = gro.film_id " +
            "WHERE fl.user_id = ? ORDER BY gro.avg_rate DESC";
    private static final String FIND_TOP_RATED_QUERY_BY_GENRE_AND_YEAR = "SELECT  " + fields + ", AVG(fl.rate) AS avg_rate " +
            "FROM films AS f " +
            "LEFT JOIN film_likes AS fl ON (fl.film_id=f.id) " +
            "WHERE f.id IN (SELECT fg.film_id FROM film_genres AS fg WHERE fg.genre_id = ?) AND EXTRACT(YEAR FROM f.release_date) = ? " +
            "GROUP BY " + fields +
            " ORDER BY avg_rate DESC " +
            "LIMIT ?";
    private static final String FIND_TOP_RATED_QUERY_BY_GENRE = "SELECT " + fields + ", AVG(fl.rate) AS avg_rate " +
            "FROM films AS f " +
            "LEFT JOIN film_likes AS fl ON (fl.film_id=f.id) " +
            "WHERE f.id IN (SELECT fg.film_id FROM film_genres AS fg WHERE fg.genre_id = ?) " +
            "GROUP BY " + fields +
            " ORDER BY avg_rate DESC " +
            "LIMIT ?";
    private static final String FIND_TOP_RATED_QUERY_BY_YEAR = "SELECT " + fields + ", AVG(fl.rate) AS avg_rate " +
            "FROM films AS f " +
            "LEFT JOIN film_likes AS fl ON (fl.film_id=f.id) " +
            "WHERE EXTRACT(YEAR FROM f.release_date) = ? " +
            "GROUP BY " + fields +
            " ORDER BY avg_rate DESC " +
            "LIMIT ?";

    private static final String FIND_ALL_BY_TITLE_CONTEXT = "SELECT f.*, AVG(fl.rate) AS avg_rate " +
            "FROM film_likes fl " +
            "RIGHT JOIN films f ON fl.film_id = f.id " +
            "WHERE LOWER(f.title) LIKE ? " +
            "GROUP BY f.id " +
            "ORDER BY avg_rate DESC";
    private static final String FIND_ALL_BY_DIRECTOR_CONTEXT = "SELECT f.*, AVG(fl.rate) AS avg_rate " +
            "FROM film_likes fl " +
            "RIGHT JOIN films f ON fl.film_id = f.id " +
            "WHERE f.id IN " +
            "(SELECT fd.film_id FROM films_directors AS fd " +
            "WHERE fd.director_id IN (SELECT d.id FROM directors AS d WHERE LOWER(d.name) LIKE ?)) " +
            "GROUP BY f.id " +
            "ORDER BY avg_rate DESC";
    private static final String FIND_ALL_BY_TITLE_AND_DIRECTOR_CONTEXT =
            "SELECT f.*, AVG(fl.rate) AS avg_rate " +
                    "FROM film_likes fl " +
                    "RIGHT JOIN films f ON fl.film_id = f.id " +
                    "WHERE (LOWER(f.title) LIKE ?) OR " +
                    "f.id IN (SELECT fd.film_id FROM films_directors AS fd " +
                    "WHERE fd.director_id IN (SELECT d.id FROM directors AS d WHERE LOWER(d.name) LIKE ?)) " +
                    "GROUP BY f.id " +
                    "ORDER BY avg_rate DESC";

    private static final String FIND_RECOMMENDED_FOR_USER_QUERY = "SELECT " + fields + " FROM " + tableName + " " +
            """
                    INNER JOIN film_likes fl ON (fl.film_id = id)
                    WHERE fl.user_id IN (
                    SELECT user_id
                    FROM film_likes WHERE film_id IN (
                    	SELECT fl.film_id
                    	FROM film_likes fl WHERE user_id = ? AND rate > 5) AND USER_ID <> ?
                    GROUP BY user_id
                    ORDER BY count(FILM_ID) DESC
                    LIMIT 1) AND fl.rate > 5
                    AND fl.film_id NOT IN(
                    	SELECT fl.film_id
                    	FROM film_likes fl WHERE user_id = ?)
                    ORDER BY fl.rate DESC
                    """;

    private static final String FIND_RECOMMENDED_FOR_USER_BY_RATES_QUERY = "SELECT " + fields +
            " FROM " + tableName + " " +
            """
                    INNER JOIN film_likes fl ON (id = fl.FILM_ID)
                    AND fl.rate > 5
                    AND fl.USER_ID IN (
                            SELECT fl.user_id
                            FROM FILM_LIKES fl
                            INNER JOIN (
                            SELECT fl.film_id AS film_id,
                                                    (fl.RATE > 5) AS is_positive_rate
                    FROM FILM_LIKES fl WHERE fl.USER_ID = ?
                            ) AS user_data
                    ON (
                            FL.FILM_ID = user_data.film_id
                            AND ((fl.RATE > 5) = user_data.is_positive_rate)
                    AND fl.USER_ID <> ?
                            )
                    GROUP BY fl.USER_ID
                    ORDER BY COUNT(fl.film_id) DESC
                    LIMIT 1
                            )
                    AND fl.film_id NOT IN (
                            SELECT fl.film_id
                            FROM FILM_LIKES fl WHERE fl.USER_ID = ?
                    )
                    """;

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
        List<Film> films = findMany(FIND_TOP_RATED_QUERY, count);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public void addLike(Integer filmId, Integer userId, Double rate) {
        update(INSERT_LIKES_QUERY, filmId, userId, rate);
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
        baseList.forEach(this::setFilmMpaGenresDirectors);
        return baseList;
    }

    @Override
    public Film getElement(Integer id) {
        Film film = findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Не найден фильм с id = " + id));
        setFilmMpaGenresDirectors(film);
        return film;
    }

    @Override
    public List<Film> getSortedFilmsByYear(Integer id) {
        List<Film> films = findMany(GET_SORTED_FILMS_BY_YEAR, id);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public List<Film> getSortedFilmsByLikes(Integer id) {
        List<Film> films = findMany(GET_SORTED_FILMS_BY_LIKES, id);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public List<Film> getSortedFilmsByRates(Integer id) {
        List<Film> films = findMany(GET_SORTED_FILMS_BY_RATES, id);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    // заполняет в фильме рейтинг, коллекции жанров, режиссеров и лайков по данным из БД
    private void setFilmMpaGenresDirectors(Film film) {
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

    public List<Film> findFilmsBySearchParameters(SearchParams searchParams) {
        List<Film> baseList = new ArrayList<>();
        if (searchParams.isNeedTitle() && !searchParams.isNeedDirector()) {
            baseList = findMany(FIND_ALL_BY_TITLE_CONTEXT, searchParams.getQuery());
        }
        if (searchParams.isNeedDirector() && !searchParams.isNeedTitle()) {
            baseList = findMany(FIND_ALL_BY_DIRECTOR_CONTEXT, searchParams.getQuery());
        }
        if (searchParams.isNeedDirector() && searchParams.isNeedTitle()) {
            baseList = findMany(FIND_ALL_BY_TITLE_AND_DIRECTOR_CONTEXT,
                    searchParams.getQuery(), searchParams.getQuery());
        }
        baseList.forEach(this::setFilmMpaGenresDirectors);
        return baseList;
    }

    @Override
    public List<Film> getPopularFilmsByGenreAndYear(Integer genreId, Integer year, Integer count) {
        List<Film> films = findMany(FIND_TOP_RATED_QUERY_BY_GENRE_AND_YEAR, genreId, year, count);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public List<Film> getPopularFilmsByGenre(Integer genreId, Integer count) {
        List<Film> films = findMany(FIND_TOP_RATED_QUERY_BY_GENRE, genreId, count);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public List<Film> getPopularFilmsByYear(Integer year, Integer count) {
        List<Film> films = findMany(FIND_TOP_RATED_QUERY_BY_YEAR, year, count);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }

    @Override
    public List<Film> getRecommendedFilms(Integer userId) {
        List<Film> films = findMany(FIND_RECOMMENDED_FOR_USER_BY_RATES_QUERY, userId, userId, userId);
        films.forEach(this::setFilmMpaGenresDirectors);
        return films;
    }
}
