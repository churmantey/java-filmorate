package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmLikesService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmLikesController {

    private final FilmLikesService filmLikesService;

    //PUT /films/{id}/like/{userId}
    @PutMapping({"/{id}/like/{userId}", "/{id}/like/{userId}/{rate}"})
    public FilmDto addLike(@PathVariable Integer id,
                           @PathVariable Integer userId,
                           @Valid @PathVariable(required = false) @Range(min = 1, max = 10) Double rate) {
        if (rate == null) {
            rate = 6.0;
        }
        log.info("PUT add like film {} , user {}, rate {}", id, userId, rate);
        FilmDto filmDto = filmLikesService.addLike(id, userId, rate);
        log.info("К film {} добавлен лайк user {}, rate {}", id, userId, rate);
        return filmDto;
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto removeLike(@PathVariable Integer id,
                              @PathVariable Integer userId) {
        log.info("DELETE like film {} , user {}", id, userId);
        FilmDto filmDto = filmLikesService.removeLike(id, userId);
        log.info("Удален лайк film {} от user {}", id, userId);
        return filmDto;
    }

    //GET /films/popular?count={limit}&genreId={genreId}&year={year}
    @GetMapping("/popular")
    public List<FilmDto> getPopularByYearAndGenre(@RequestParam(defaultValue = "10") int count,
                                                  @RequestParam(required = false) Integer genreId,
                                                  @RequestParam(required = false) Integer year) {
        log.info("GET first {} popular films by genre {} and year {}", count, genreId, year);
        List<FilmDto> resList = filmLikesService.getPopularByYearAndGenre(count, genreId, year);
        log.info("Получены first {} popular films by genre {} and year {}", count, genreId, year);
        return resList;
    }
}
