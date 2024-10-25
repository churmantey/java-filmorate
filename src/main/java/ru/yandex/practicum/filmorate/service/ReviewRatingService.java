package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.ReviewDto;

public interface ReviewRatingService {

    ReviewDto addLike(Integer id, Integer userId);

    ReviewDto addDislike(Integer id, Integer userId);

    ReviewDto deleteLike(Integer id, Integer userId);

    ReviewDto deleteDislike(Integer id, Integer userId);
}
