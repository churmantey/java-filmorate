package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.service.ReviewRatingService;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewRatingController {
    private final ReviewRatingService reviewService;

    @PutMapping("/{id}/like/{userId}")
    public ReviewDto addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("PUT addLike id = {}, userId = {}", id, userId);
        ReviewDto reviewDto = reviewService.addLike(id, userId);
        log.info("Added like id = {}, userId = {}", id, userId);
        return reviewDto;
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ReviewDto addDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("PUT addDislike id = {}, userId = {}", id, userId);
        ReviewDto reviewDto = reviewService.addDislike(id, userId);
        log.info("Added dislike id = {}, userId = {}", id, userId);
        return reviewDto;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ReviewDto deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("DELETE deleteLike id = {}, userId = {}", id, userId);
        ReviewDto reviewDto = reviewService.deleteLike(id, userId);
        log.info("Deleted like id = {}, userId = {}", id, userId);
        return reviewDto;
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ReviewDto deleteDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("DELETE deleteDislike id = {}, userId = {}", id, userId);
        ReviewDto reviewDto = reviewService.deleteDislike(id, userId);
        log.info("Deleted dislike id = {}, userId = {}", id, userId);
        return reviewDto;
    }
}
