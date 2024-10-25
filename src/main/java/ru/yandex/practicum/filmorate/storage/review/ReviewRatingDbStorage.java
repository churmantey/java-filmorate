package ru.yandex.practicum.filmorate.storage.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
@RequiredArgsConstructor
public class ReviewRatingDbStorage implements ReviewRatingStorage {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void addRating(Integer id, Integer userId, String type) {

        String sql = """
        INSERT INTO reviews_ratings(review_id, user_id, rating_type)
        VALUES (:reviewId, :userId, :ratingType);
""";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("reviewId", id);
        params.addValue("userId", userId);
        params.addValue("ratingType", type);
        jdbc.update(sql, params);
    }

    @Override
    public void deleteRating(Integer id, Integer userId) {
        String sql = "DELETE FROM reviews_ratings WHERE REVIEW_ID = :reviewId AND USER_ID = :userId;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("reviewId", id);
        params.addValue("userId", userId);
        jdbc.update(sql, params);
    }

    @Override
    public boolean isUserLiked(Integer id, Integer userId) {
        String sql = "SELECT COUNT(*) FROM REVIEWS_RATINGS WHERE REVIEW_ID = :id AND " +
                "USER_ID = :userId AND RATING_TYPE LIKE 'LIKE';";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        Integer rows = jdbc.queryForObject(sql, params, Integer.class);

        return rows > 0;
    }

    @Override
    public boolean isUserDisliked(Integer id, Integer userId) {
        String sql = "SELECT COUNT(*) FROM REVIEWS_RATINGS WHERE REVIEW_ID = :id " +
                "AND USER_ID = :userId AND RATING_TYPE LIKE 'DISLIKE';";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        Integer rows = jdbc.queryForObject(sql, params, Integer.class);
        return rows > 0;
    }

}
