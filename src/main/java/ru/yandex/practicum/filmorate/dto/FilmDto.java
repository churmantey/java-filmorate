package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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
