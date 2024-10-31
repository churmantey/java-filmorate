package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotation.Update;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Integer id) {
        log.info("GET get by id = {}", id);
        ReviewDto reviewDto = reviewService.getReviewById(id);
        log.info("Got review by id {}", reviewDto);
        return reviewDto;
    }

    @GetMapping
    public List<ReviewDto> getByFilmId(@RequestParam(defaultValue = "0") Integer filmId,
                                  @RequestParam(defaultValue = "10") Integer count) {
        log.info("GET get by film = {} and count = {}", filmId, count);
        List<ReviewDto> revList = reviewService.getByFilmId(filmId, count);
        log.info("Got list of review by film = {} and count = {}", filmId, count);
        return revList;
    }

    @PostMapping
    public ReviewDto createReview(@Valid @RequestBody Review review) {
        log.info("POST create review with body: {}", review);
        ReviewDto reviewDto = reviewService.createReview(review);
        log.info("Created review with body: {}", reviewDto);
        return reviewDto;
    }

    @PutMapping
    public ReviewDto updateReview(@Validated(Update.class) @RequestBody Review review) {
        log.info("PUT update review with body: {}", review);
        ReviewDto reviewDto = reviewService.updateReview(review);
        log.info("Updated review. New body: {}", reviewDto);
        return reviewDto;
    }

    @DeleteMapping("/{id}")
    public boolean deleteReview(@PathVariable Integer id) {
        log.info("DELETE delete by id = {}", id);
        boolean result = reviewService.deleteReview(id);
        log.info("Result of deleting review with id {} is {}", id, result);
        return result;
    }
}
