package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class NewFilmRequest {
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @Positive
    private Integer duration;

    @NotNull
    private Rating mpa;

    private LocalDate releaseDate;
    private Set<Genre> genres;
    private Set<Director> directors;
}
