package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.util.List;

@Repository
public class RatingDbStorage extends BaseDbStorage<Rating> implements RatingStorage {
    private static final String FIND_ALL_QUERY = "SELECT id, name, description FROM ratings";

    private static final String FIND_FILM_RATING_QUERY = """
            SELECT id,
                name,
                description
            FROM ratings
            WHERE id = ?
            """;
    private static final String FIND_RATING_QUERY = FIND_ALL_QUERY + " WHERE id = ?";

    public RatingDbStorage(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Rating getElement(Integer ratingId) {
        return findOne(FIND_FILM_RATING_QUERY, ratingId)
                .orElseThrow(() -> new NotFoundException("Не найден рейтинг с id = " + ratingId));
    }

    @Override
    public boolean isValidRatingId(Integer id) {
        if (id == null) return false;
        return findOne(FIND_RATING_QUERY, id).isPresent();
    }

    @Override
    public List<Rating> getAllElements() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Rating addElement(Rating element) {
        return null;
    }

    @Override
    public boolean deleteElement(Rating element) {
        return false;
    }

    @Override
    public Rating updateElement(Rating newElement) {
        return null;
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return false;
    }
}
