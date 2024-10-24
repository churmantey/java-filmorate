package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.ReviewRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReviewRatingRowMapper implements RowMapper<ReviewRating> {
    @Override
    public ReviewRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReviewRating(
                rs.getInt("review_id"),
                rs.getInt("user_id")
        );
    }
}
