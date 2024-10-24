package ru.yandex.practicum.filmorate.storage.review;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.ReviewRating;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

@Repository
public class ReviewRatingDbStorage extends BaseDbStorage<ReviewRating> implements ReviewRatingStorage {
    private static final String DELETE_RATING_QUERY = "DELETE FROM reviews_ratings WHERE review_id = ? AND user_id = ?";
    private static final String INSERT_RATING_QUERY = "INSERT INTO reviews_ratings" +
            " (review_id, user_id) " +
            " VALUES (?, ?)";
    private static final String FIND_USER_IN_RATINGS_QUERY = "SELECT review_id, user_id " +
            "FROM reviews_ratings WHERE user_id = ?";

    public ReviewRatingDbStorage(JdbcTemplate jdbc, RowMapper<ReviewRating> mapper) {
        super(jdbc, mapper);
    }

    public boolean addRating(Integer id, Integer userId) {
        Integer rows = insert(INSERT_RATING_QUERY, id, userId);

        return rows > 0;
    }

    public boolean deleteRating(Integer id, Integer userId) {
        return delete(DELETE_RATING_QUERY, id, userId);
    }

    public boolean isUserExist(Integer id) {
        return !findMany(FIND_USER_IN_RATINGS_QUERY, id).isEmpty();
    }
}
