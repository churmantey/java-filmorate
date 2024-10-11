package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
public class FilmDto {

    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private IdEntity mpa;
    private final Set<IdEntity> likes;
    private final Set<IdEntity> genres;

    public FilmDto() {
        this.likes = new LinkedHashSet<>();
        this.genres = new LinkedHashSet<>();
    }

}
