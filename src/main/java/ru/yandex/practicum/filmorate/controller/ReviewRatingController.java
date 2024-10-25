package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewRatingService;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewRatingController {
    private final ReviewRatingService reviewService;

    @PutMapping("/{id}/like/{userId}")
    public ReviewDto addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("PUT addLike id = {}, userId = {}", id, userId);
        return reviewService.addLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ReviewDto addDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("PUT addDislike id = {}, userId = {}", id, userId);
        return reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ReviewDto deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("DELETE deleteLike id = {}, userId = {}", id, userId);
        return reviewService.deleteLike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ReviewDto deleteDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("DELETE deleteDislike id = {}, userId = {}", id, userId);
        return reviewService.deleteDislike(id, userId);
    }
}
