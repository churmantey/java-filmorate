package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        log.info("GET all genres");
        List<Genre> genres = genreService.getAllGenres();
        log.info("Got list of all genres");
        return genres;
    }

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable Integer genreId) {
        log.info("GET genre {}", genreId);
        Genre genre = genreService.getGenreById(genreId);
        log.info("Got {}", genre);
        return genre;
    }
}
