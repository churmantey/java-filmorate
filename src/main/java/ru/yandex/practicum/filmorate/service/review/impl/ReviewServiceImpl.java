package ru.yandex.practicum.filmorate.service.review.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.dto.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.review.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage storage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public ReviewDto get(Integer id) {
        log.info("Get review by id: {}", id);
        return ReviewMapper.mapToReviewDto(storage.getElement(id));
    }

    @Override
    public List<ReviewDto> getByFilmId(Integer filmId, Integer count) {
        log.info("Get reviews by filmId and count: {}, {}", filmId, count);

        log.info("Checking count: {}", count);
        if (count == null) {
            count = 10;
        }

        log.info("Checking filmId: {}", filmId);
        if (filmId == null) {
            log.info("FilmId is null");
            return storage.getByFilm(count).stream()
                    .map(ReviewMapper::mapToReviewDto).toList();
        }

        return storage.getByFilm(filmId, count)
                .stream()
                .map(ReviewMapper::mapToReviewDto).toList();
    }

    @Override
    public ReviewDto create(Review review) {
        log.info("Making checks");
        userAndFilmCheck(review);

        log.info("Setting up 'useful' field");
        if (review.getUseful() == null) {
            review.setUseful(0);
        }

        log.info("Calling storage method");
        return ReviewMapper.mapToReviewDto(storage.addElement(review));
    }

    @Override
    public ReviewDto update(Review review) {
        log.info("Update review: {}", review);
        return ReviewMapper.mapToReviewDto(storage.updateElement(review));
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Delete review by id: {}", id);
        return storage.deleteElementById(id);
    }

    private void userAndFilmCheck(Review review) {
        if (review.getUserId() == null) {
            throw new IllegalArgumentException("User is null");
        }

        if (userStorage.getElement(review.getUserId()) == null) {
            throw new NotFoundException("User not found");
        }

        if (review.getFilmId() == null) {
            throw new IllegalArgumentException("Film is null");
        }

        if (filmStorage.getElement(review.getFilmId()) == null) {
            throw new NotFoundException("Film not found");
        }

//        if (review.isPositive() == null) {
//            throw new IllegalArgumentException("Review is null");
//        }
    }
}
