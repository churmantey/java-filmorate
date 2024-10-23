package ru.yandex.practicum.filmorate.storage.director;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class DirectorDbStorage extends BaseDbStorage<Director> implements DirectorStorage {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Director> mapper;
    private static final String FIND_ALL_QUERY = "SELECT * FROM directors";
    private static final String INSERT_QUERY = "INSERT INTO directors (name) VALUES(?)";
    private static final String FIND_BY_ID = "SELECT * FROM directors WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE directors SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM directors WHERE id = ?";
    private static final String GET_DIRECTORS_BY_ID = "SELECT * FROM directors WHERE id in (%S)";
    private static final String GET_DIRECTORS_IDS = "SELECT director_id FROM films_directors WHERE film_id = ?";
    private static final String MERGE_QUERY = "MERGE INTO films_directors KEY(film_id, director_id) VALUES(?, ?)";
    private static final String GET_DIRECTORS_FOR_ONE_FILM = "SELECT * FROM DIRECTORS d WHERE id IN " +
            "(SELECT director_id FROM FILMS_DIRECTORS fd WHERE film_id = ?)";

    public DirectorDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Director> mapper) {
        super(jdbcTemplate, mapper);
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public Director addElement(Director director) {
        Integer newId = insert(INSERT_QUERY, director.getName());
        director.setId(newId);
        return director;
    }

    @Override
    public boolean deleteElement(Director element) {
        return false;
    }

    @Override
    public Director updateElement(Director director) {
        update(UPDATE_QUERY, director.getName(), director.getId());
        return director;
    }

    @Override
    public List<Director> getAllElements() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Director getElement(Integer id) {
        return findOne(FIND_BY_ID, id)
                .orElseThrow(() -> new NotFoundException("Не найден режиссер с id = " + id));
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return delete(DELETE_QUERY, id);
    }

    @Override
    public List<Director> getDirectorByIds(Set<Integer> directorIds) {
        String inSql = String.join(",", Collections.nCopies(directorIds.size(), "?"));
        return jdbcTemplate.query(String.format(GET_DIRECTORS_BY_ID, inSql), mapper, directorIds.toArray());
    }

    @Override
    public Set<Integer> getDirectorIdsOfFilm(Integer id) {
        return new LinkedHashSet<>(retrieveIdList(GET_DIRECTORS_IDS, id));
    }

    @Override
    public void insertFilmAndDirectors(Integer film_id, Set<Integer> directorIds) {
        jdbcTemplate.batchUpdate(
                MERGE_QUERY,

                new BatchPreparedStatementSetter() {

                    private final List<Integer> directorIdsList = new ArrayList<>(directorIds);

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, film_id);
                        ps.setInt(2, directorIdsList.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return directorIds.size();
                    }
                }
        );
    }

    @Override
    public List<Director> getDirectorsByFilmId(Integer id) {
        return findMany(GET_DIRECTORS_FOR_ONE_FILM, id);
    }
}
