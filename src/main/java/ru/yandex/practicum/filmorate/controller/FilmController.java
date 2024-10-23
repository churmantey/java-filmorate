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

    @GetMapping("/director/{directorId}")
    public List<FilmDto> getFilmsByDirectorSorted(@PathVariable Integer directorId,
                                                  @RequestParam(name = "sortBy") String sort) {
        log.info("Пришел GET запрос /films/director/{} с сортировкой по {}", directorId, sort);
        List<FilmDto> films = filmService.getSortedFilms(directorId, sort);
        log.info("Отправлен GET ответ с телом {}", films);
        return films;
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

}
