package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Film.
 */

@Slf4j
@Data
public class Film {

    private static final LocalDate CINEMA_EPOCH = LocalDate.of(1895, 12, 28);

    private Integer id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @PastOrPresent
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    @NotNull
    private Rating mpa;

    @Getter
    private final Set<Genre> genres;

    private final Set<Director> directors;

    public Film(String name, String description, LocalDate releaseDate, Integer duration, Integer mpaId) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Rating(mpaId);
        this.genres = new LinkedHashSet<>();
        this.directors = new LinkedHashSet<>();
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Integer mpaId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Rating(mpaId);
        this.genres = new LinkedHashSet<>();
        this.directors = new LinkedHashSet<>();
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = null;
        this.genres = new LinkedHashSet<>();
        this.directors = new LinkedHashSet<>();
    }

    public void validate() {
        if (this.getReleaseDate() == null || this.getReleaseDate().isBefore(CINEMA_EPOCH)) {
            String message = "Некорректная дата выхода фильма";
            log.debug(message);
            throw new ValidationException(message);
        }
    }

}
