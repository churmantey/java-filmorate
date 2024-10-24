package ru.yandex.practicum.filmorate.storage.review;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;

import java.util.List;

@Repository
@Qualifier("ReviewDbStorage")
public class ReviewDbStorage extends BaseDbStorage<Review> implements ReviewStorage {
    private static final String tableName = "reviews";
    private static final String fields = "id, content, is_positive, user_id, film_id, useful";
    private static final String FIND_ALL_QUERY = "SELECT " + fields + " from " + tableName;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String FIND_ALL_BY_FILM_ID_QUERY = FIND_ALL_QUERY + " WHERE film_id = ? LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO " + tableName +
            " (content, is_positive, user_id, film_id, useful) " +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE " + tableName + " SET " +
            "content = ?, is_positive = ?, user_id = ?, film_id = ?, useful = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM " + tableName + " WHERE id = ?";

    public ReviewDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Review> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Review getElement(Integer id) {
        return findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new NotFoundException("Could not find review with id: " + id));
    }

    public List<Review> getByFilm(int id, int count) {
        return findMany(FIND_ALL_BY_FILM_ID_QUERY, id, count);
    }

    public List<Review> getByFilm(int count) {
        String sql = FIND_ALL_BY_FILM_ID_QUERY + " LIMIT ?";
        return findMany(sql, count);
    }

    @Override
    public Review addElement(Review review) {
        Integer id = insert(INSERT_QUERY,
                review.getContent(),
                review.isPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful()
        );

        review.setId(id);
        return review;
    }

    @Override
    public boolean deleteElement(Review element) {
        return false;
    }

    @Override
    public List<Review> getAllElements() {
        return List.of();
    }

    @Override
    public Review updateElement(Review newReview) {
        update(UPDATE_QUERY,
                newReview.getId(),
                newReview.getContent(),
                newReview.isPositive(),
                newReview.getUserId(),
                newReview.getFilmId(),
                newReview.getUseful()
        );

        return newReview;
    }

    @Override
    public boolean deleteElementById(Integer id) {
        return delete(DELETE_QUERY, id);
    }
}
