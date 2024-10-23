package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewRatingController {

    @PutMapping("/reviews/{id}/like/{userId}")
    public Review addLike(@PathVariable long id, @PathVariable long userId) {

    }

    @PutMapping("/reviews/{id}/dislike/{userId}")
    public Review addDislike(@PathVariable long id, @PathVariable long userId) {

    }

    @DeleteMapping("/reviews/{id}/like/{userId}")
    public Review deleteLike(@PathVariable long id, @PathVariable long userId) {

    }

    @DeleteMapping("/reviews/{id}/dislike/{userId}")
    public Review deleteDislike(@PathVariable long id, @PathVariable long userId) {

    }
}
