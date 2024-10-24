package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PutMapping("/{id}/like/{userId}")
    public FilmDto addLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        log.info("PUT add like film {} , user {}", id, userId);
        return filmLikesService.addLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto removeLike(@PathVariable Integer id,
                              @PathVariable Integer userId) {
        log.info("DELETE like film {} , user {}", id, userId);
        return filmLikesService.removeLike(id, userId);
    }

    //GET /films/popular?count={limit}&genreId={genreId}&year={year}
    @GetMapping("/popular")
    public List<FilmDto> getPopularByYearAndGenre(@RequestParam(defaultValue = "10") int count,
                                                  @RequestParam(required = false) Integer genreId,
                                                  @RequestParam(required = false) Integer year) {
        log.info("GET first {} popular films by genre {} and year {}", count, genreId, year);
        if (genreId == null && year == null) {
            return filmLikesService.getPopular(count);
        }
        if (genreId != null && year == null) {
            return filmLikesService.getPopularFilmsByGenre(genreId, count);
        }
        if (genreId == null && year != null) {
            return filmLikesService.getPopularFilmsByYear(year, count);
        }
        return filmLikesService.getPopularFilmsByGenreAndYear(genreId, year, count);

    }
}
