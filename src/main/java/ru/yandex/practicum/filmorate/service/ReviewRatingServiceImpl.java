package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.dto.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.review.ReviewRatingStorage;
import ru.yandex.practicum.filmorate.storage.review.ReviewStorage;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewRatingServiceImpl implements ReviewRatingService {
    private final ReviewStorage storage;
    private final ReviewRatingStorage ratingStorage;
    private final ReviewMapper reviewMapper;

    private static final String RATING_TYPE_LIKE = "LIKE";
    private static final String RATING_TYPE_DISLIKE = "DISLIKE";

    @Override
    public ReviewDto addLike(Integer id, Integer userId) {
        log.info("Checking if user already liked review with id = {}", id);
        if (ratingStorage.isUserLiked(id, userId)) {
            throw new IllegalArgumentException("User already rated");
        }

        Review review = storage.getElement(id);
        log.info("Gained review = {}", review);

        log.info("Checking if user already disliked review");
        if (ratingStorage.isUserDisliked(id, userId)) {
            log.info("Removing previous dislike and updating useful rating for review");
            review.setUseful(review.getUseful() + 1);
            ratingStorage.deleteRating(id, userId);
        }

        log.info("Updating useful rating for review");
        review.setUseful(review.getUseful() + 1);
        ratingStorage.addRating(id, userId, RATING_TYPE_LIKE);
        storage.updateElement(review);

        log.info("Returning updated review = {}", review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto addDislike(Integer id, Integer userId) {
        log.info("Checking if user already disliked review with id = {}", id);
        if (ratingStorage.isUserDisliked(id, userId)) {
            throw new IllegalArgumentException("User already rated");
        }

        Review review = storage.getElement(id);
        log.info("Gained review = {}", review);

        log.info("Checking if user already liked review");
        if (ratingStorage.isUserLiked(id, userId)) { // id = 15, userId = 6
            log.info("Removing previous like and updating useful rating for review");
            review.setUseful(review.getUseful() - 1);
            ratingStorage.deleteRating(id, userId);
        }

        log.info("Updating useful rating for review");
        review.setUseful(review.getUseful() - 1);
        ratingStorage.addRating(id, userId, RATING_TYPE_DISLIKE);
        storage.updateElement(review);

        log.info("Returning updated review = {}", review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto deleteLike(Integer id, Integer userId) {
        Review review = storage.getElement(id);
        log.info("Gained review = {}", review);

        log.info("Updating useful rating for review and deleting rate");
        review.setUseful(review.getUseful() - 1);
        ratingStorage.deleteRating(id, userId);
        storage.updateElement(review);

        log.info("Returning updated review = {}", review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto deleteDislike(Integer id, Integer userId) {
        Review review = storage.getElement(id);
        log.info("Gained review = {}", review);

        log.info("Updating useful rating for review and deleting rate");
        review.setUseful(review.getUseful() + 1);
        ratingStorage.deleteRating(id, userId);
        storage.updateElement(review);

        log.info("Returning updated review = {}", review);
        return reviewMapper.toDto(review);
    }
}
