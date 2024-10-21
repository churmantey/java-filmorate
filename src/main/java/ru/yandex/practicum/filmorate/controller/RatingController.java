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
        return ratingService.getAllMpa();
    }

    @GetMapping("/{ratingId}")
    public Rating getMpa(@PathVariable Integer ratingId) {
        log.info("GET rating {}", ratingId);
        return ratingService.getMpaById(ratingId);
    }
}
