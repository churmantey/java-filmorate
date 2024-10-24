package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    ReviewDto get(Integer id);

    List<ReviewDto> getByFilmId(Integer filmId, Integer count);

    Review create(Review review);

    Review update(Review review);

    boolean delete(Integer id);
}
