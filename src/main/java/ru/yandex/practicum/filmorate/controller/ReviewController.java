package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @GetMapping("/reviews/{id}")
    public Review getById(@RequestParam int id) {
        return null;
    }

    @GetMapping
    public List<Review> getByFilm(@RequestParam(defaultValue = "0") long filmId,
                                  @RequestParam(defaultValue = "10") int count) {
        return null;
    }

    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        return null;
    }

    @PutMapping
    public Review update(@Valid @RequestBody Review review) {
        return null;
    }

    @DeleteMapping("/reviews/{id}")
    public void delete(@PathVariable long id) {

    }
}
