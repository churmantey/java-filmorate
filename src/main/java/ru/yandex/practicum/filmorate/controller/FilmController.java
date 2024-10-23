package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<FilmDto> getAllFilms() {
        log.info("GET films");
        return filmService.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public FilmDto getFilm(@PathVariable Integer filmId) {
        log.info("GET film {}", filmId);
        return filmService.getFilmById(filmId);
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody NewFilmRequest newFilmRequest) {
        log.info("POST film {}", newFilmRequest);
        return filmService.createFilm(newFilmRequest);
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody UpdateFilmRequest updateFilmRequest) {
        log.info("PUT film {}", updateFilmRequest);
        return filmService.updateFilm(updateFilmRequest);
    }

    @GetMapping("/common")
    public List<FilmDto> getCommonFilms(@RequestParam Integer userId, @RequestParam Integer friendId) {
        log.info("GET commonFilms from userId {}, friendId {}", userId, friendId);
        List<FilmDto> commonFilms = filmService.getCommonFilmsLikesByUsers(userId, friendId);
        log.info("GET commonFilms films {}", commonFilms);
        return commonFilms;
    }
}
