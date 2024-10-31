package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<Rating> getAllMpa() {
        log.info("GET all ratings");
        List<Rating> ratings = ratingService.getAllMpa();
        log.info("Got list of all ratings");
        return ratings;
    }

    @GetMapping("/{ratingId}")
    public Rating getMpaById(@PathVariable Integer ratingId) {
        log.info("GET rating {}", ratingId);
        Rating rating = ratingService.getMpaById(ratingId);
        log.info("Got {}", rating);
        return rating;
    }
}
