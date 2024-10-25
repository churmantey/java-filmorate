package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.dto.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
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
        log.info("Getting review by id: {}", id);
        return ReviewMapper.mapToReviewDto(storage.getElement(id));
    }

    @Override
    public List<ReviewDto> getByFilmId(Integer filmId, Integer count) {
        log.info("Getting reviews by filmId and count: {}, {}", filmId, count);

        log.info("Checking count: {}", count);
        if (count == null) {
            log.info("Count is null, setting to default value");
            count = 10;
        }

        log.info("Checking filmId: {}", filmId);
        if (filmId == null) {
            log.info("FilmId is null, returning 10 most rated films");
            return storage.getByFilm(count).stream()
                    .map(ReviewMapper::mapToReviewDto).toList();
        }

        return storage.getByFilm(filmId, count)
                .stream()
                .map(ReviewMapper::mapToReviewDto).toList();
    }

    @Override
    public ReviewDto create(Review review) {
        log.info("Making checks for user and film");
        userAndFilmCheck(review);

        log.info("Setting up 'useful' field");
        if (review.getUseful() == null) {
            review.setUseful(0);
        }

        log.info("Creating new review instance in database");
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
    }
}
