package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    ReviewDto get(Integer id);

    List<ReviewDto> getByFilmId(Integer filmId, Integer count);

    ReviewDto create(Review review);

    ReviewDto update(Review review);

    boolean delete(Integer id);
}
