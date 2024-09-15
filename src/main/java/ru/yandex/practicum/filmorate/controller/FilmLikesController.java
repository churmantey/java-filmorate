package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmLikesService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmLikesController {

    private final FilmLikesService filmLikesService;

    //GET /films/popular?count={count}
    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("GET popular {}", count);
        return filmLikesService.getTopRatedFilms(count);
    }

    //PUT /films/{id}/like/{userId}
    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) {
        log.info("PUT add like film {} , user {}", id, userId);
        return filmLikesService.addLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        log.info("DELETE like film {} , user {}", id, userId);
        return filmLikesService.removeLike(id, userId);
    }

}
