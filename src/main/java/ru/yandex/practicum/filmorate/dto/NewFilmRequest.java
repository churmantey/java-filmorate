package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
    @PastOrPresent
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    @NotNull
    private Rating mpa;
    private Set<Genre> genres;
}
