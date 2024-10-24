package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotation.Update;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewDto getById(@PathVariable Integer id) {
        return reviewService.get(id);
    }

    @GetMapping
    public List<ReviewDto> getByFilm(@RequestParam(defaultValue = "0") Integer filmId,
                                  @RequestParam(defaultValue = "10") Integer count) {
        log.info("Request params filmId = {}, count = {}", filmId, count);
        return reviewService.getByFilmId(filmId, count);
    }

    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        log.info("Create review: {}", review);
        return reviewService.create(review);
    }

    @PutMapping("/{id}")
    public Review update(@Validated(Update.class) @RequestBody Review review) {
        log.info("Update review: {}", review);
        return reviewService.update(review);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return reviewService.delete(id);
    }
}
