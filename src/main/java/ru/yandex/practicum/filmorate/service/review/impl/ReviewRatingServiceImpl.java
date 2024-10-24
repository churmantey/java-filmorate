package ru.yandex.practicum.filmorate.service.review.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewRatingService;
import ru.yandex.practicum.filmorate.storage.review.ReviewRatingStorage;
import ru.yandex.practicum.filmorate.storage.review.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewRatingServiceImpl implements ReviewRatingService {
    private final ReviewStorage storage;
    private final ReviewRatingStorage ratingStorage;
    private final UserStorage userStorage;

    @Override
    public Review addLike(Integer id, Integer userId) {
        Review review = getReviewAndCheck(id, userId);

        review.setUseful(review.getUseful() + 1);
        storage.updateElement(review);
        ratingStorage.addRating(id, userId);

        return review;
    }

    @Override
    public Review addDislike(Integer id, Integer userId) {
        Review review = getReviewAndCheck(id, userId);

        review.setUseful(review.getUseful() - 1);
        storage.updateElement(review);
        ratingStorage.addRating(id, userId);

        return review;
    }

    @Override
    public Review deleteLike(Integer id, Integer userId) {
        Review review = storage.getElement(id);
        ratingStorage.deleteRating(review.getId(), userId);

        review.setUseful(review.getUseful() - 1);
        storage.updateElement(review);
        ratingStorage.deleteRating(id, userId);

        return review;
    }

    @Override
    public Review deleteDislike(Integer id, Integer userId) {
        Review review = storage.getElement(id);
        ratingStorage.deleteRating(review.getId(), userId);

        review.setUseful(review.getUseful() + 1);
        storage.updateElement(review);
        ratingStorage.deleteRating(id, userId);

        return review;
    }

    private Review getReviewAndCheck(Integer id, Integer userId) {
        if (userStorage.getElement(userId) == null) {
            throw new NotFoundException("User not found");
        }

        if (ratingStorage.isUserExist(userId)) {
            throw new IllegalArgumentException("User already rated");
        }

        return storage.getElement(id);
    }
}
