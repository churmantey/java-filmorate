package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    Review get(Integer id);

    List<Review> getByFilmId(Integer filmId, Integer count);

    Review create(Review review);

    Review update(Review review);

    boolean delete(Integer id);
}
