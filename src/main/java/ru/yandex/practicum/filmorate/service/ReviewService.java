package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    ReviewDto getReviewById(Integer id);

    List<ReviewDto> getByFilmId(Integer filmId, Integer count);

    ReviewDto createReview(Review review);

    ReviewDto updateReview(Review review);

    boolean deleteReview(Integer id);
}
