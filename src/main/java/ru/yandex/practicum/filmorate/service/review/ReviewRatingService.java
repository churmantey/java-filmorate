package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.model.Review;

public interface ReviewRatingService {

    Review addLike(Integer id, Integer userId);

    Review addDislike(Integer id, Integer userId);

    Review deleteLike(Integer id, Integer userId);

    Review deleteDislike(Integer id, Integer userId);
}
