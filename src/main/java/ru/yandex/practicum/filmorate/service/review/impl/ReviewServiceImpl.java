package ru.yandex.practicum.filmorate.service.review.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;
import ru.yandex.practicum.filmorate.storage.review.ReviewStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage storage;

    @Override
    public Review get(Integer id) {
        log.info("Get review by id: {}", id);
        return storage.getElement(id);
    }

    @Override
    public List<Review> getByFilmId(Integer filmId, Integer count) {
        log.info("Get reviews by filmId and count: {}, {}", filmId, count);

        log.info("Checking count: {}", count);
        if (count == null) {
            count = 10;
        }

        log.info("Checking filmId: {}", filmId);
        if (filmId == null) {
            log.info("FilmId is null");
            return storage.getByFilm(count);
        }

        log.info("Returning");
        return storage.getByFilm(filmId, count);
    }

    @Override
    public Review create(Review review) {
        log.info("Create review: {}", review);
        return storage.addElement(review);
    }

    @Override
    public Review update(Review review) {
        log.info("Update review: {}", review);
        return storage.updateElement(review);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Delete review by id: {}", id);
        return storage.deleteElementById(id);
    }
}
