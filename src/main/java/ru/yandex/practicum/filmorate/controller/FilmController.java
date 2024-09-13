package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.IdGenerator;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final IdGenerator idGenerator;

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("GET films");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("POST film {}", film);
        film.validate();
        film.setId(idGenerator.getNextId());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.debug("PUT film {}", newFilm);
        newFilm.validate();
        return filmService.updateFilm(newFilm);
    }

    //PUT /films/{id}/like/{userId}
    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) {
        return filmService.addLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        return filmService.removeLike(id, userId);
    }

    //GET /films/popular?count={count}
    @GetMapping("/popular?count={count}")
    public List<Film> getPopular(@RequestParam("required = false, defaultValue = 10") int count) {
        return filmService.getTopRatedFilms(count);
    }

}
