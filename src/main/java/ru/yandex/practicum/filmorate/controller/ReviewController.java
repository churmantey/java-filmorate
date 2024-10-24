package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews/{id}")
    public Review getById(@PathVariable Integer id) {
        return reviewService.get(id);
    }

    @GetMapping
    public List<Review> getByFilm(@RequestParam(defaultValue = "0") Integer filmId,
                                  @RequestParam(defaultValue = "10") Integer count) {
        return reviewService.getByFilmId(filmId, count);
    }

    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        return reviewService.create(review);
    }

    @PutMapping
    public Review update(@Valid @RequestBody Review review) {
        return reviewService.update(review);
    }

    @DeleteMapping("/reviews/{id}")
    public boolean delete(@PathVariable Integer id) {
        return reviewService.delete(id);
    }
}
