package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validators.SearchParametersConstraint;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<FilmDto> getAllFilms() {
        log.info("GET films");
        List<FilmDto> filmList = filmService.getAllFilms();
        log.info("GET films RESPONSE {}", filmList);
        return filmList;
    }

    @GetMapping("/{filmId}")
    public FilmDto getFilmById(@PathVariable Integer filmId) {
        log.info("GET film {}", filmId);
        FilmDto filmDto = filmService.getFilmById(filmId);
        log.info("GET film RESPONSE {}", filmDto);
        return filmDto;
    }

    @GetMapping("/director/{directorId}")
    public List<FilmDto> getFilmsByDirectorSorted(@PathVariable Integer directorId,
                                                  @RequestParam(name = "sortBy") String sort) {
        log.info("Пришел GET запрос /films/director/{} с сортировкой по {}", directorId, sort);
        List<FilmDto> films = filmService.getFilmsByDirectorSorted(directorId, sort);
        log.info("Отправлен GET ответ с телом {}", films);
        return films;
    }

    @PostMapping
    public FilmDto createFilm(@Valid @RequestBody NewFilmRequest newFilmRequest) {
        log.info("POST film {}", newFilmRequest);
        FilmDto filmDto = filmService.createFilm(newFilmRequest);
        log.info("Добавлен film {}", filmDto);
        return filmDto;
    }

    @PutMapping
    public FilmDto updateFilm(@Valid @RequestBody UpdateFilmRequest updateFilmRequest) {
        log.info("PUT film {}", updateFilmRequest);
        FilmDto filmDto = filmService.updateFilm(updateFilmRequest);
        log.info("Изменен film {}", filmDto);
        return filmDto;
    }

    @DeleteMapping("/{filmId}")
    public boolean deleteFilmById(@PathVariable Integer filmId) {
        log.info("DELETE film {}", filmId);
        FilmDto film = filmService.getFilmById(filmId);
        boolean result = filmService.deleteFilmById(film.getId());
        log.info("Результат удаления {}", result);
        return result;
    }

    @GetMapping("/common")
    public List<FilmDto> getCommonFilmsLikesByUsers(@RequestParam Integer userId, @RequestParam Integer friendId) {
        log.info("GET commonFilms from userId {}, friendId {}", userId, friendId);
        List<FilmDto> commonFilms = filmService.getCommonFilmsLikesByUsers(userId, friendId);
        log.info("GET commonFilms films {}", commonFilms);
        return commonFilms;
    }

    @GetMapping("/search")
    public List<FilmDto> getFilmsByContext(@Valid @RequestParam(name = "query") @NotEmpty String query,
                                           @Valid @RequestParam(name = "by") @SearchParametersConstraint String find) {
        log.info("Search films by context {} params {}", query, find);
        List<FilmDto> foundFilms = filmService.getFilmsByContext(query, find);
        log.info("{} films was found", foundFilms.size());
        return foundFilms;
    }

}
