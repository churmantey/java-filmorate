package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.IdGenerator;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final IdGenerator idGenerator;

    public FilmController(@Autowired FilmService filmService) {
        this.filmService = filmService;
        this.idGenerator = new IdGenerator();
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("GET films");
        return filmService.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable Integer filmId) {
        log.debug("GET film {}", filmId);
        return filmService.getFilmById(filmId);
    }

    //GET /films/popular?count={count}
    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(required = false, defaultValue = "10") int count) {
        log.debug("GET popular {}", count);
        return filmService.getTopRatedFilms(count);
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
        log.debug("PUT add like film {} , user {}", id, userId);
        return filmService.addLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        log.debug("DELETE like film {} , user {}", id, userId);
        return filmService.removeLike(id, userId);
    }
}
