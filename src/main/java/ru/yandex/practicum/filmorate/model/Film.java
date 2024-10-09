package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.HashSet;
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

    private Integer mpa;

    @Getter
    private final Set<Integer> likes;
    @Getter
    private final Set<Integer> genres;

    public Film() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.releaseDate = null;
        this.duration = 0;
        this.mpa = 0;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Integer mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }
    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = 0;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public void validate() {
        if (this.getReleaseDate() == null || this.getReleaseDate().isBefore(CINEMA_EPOCH)) {
            String message = "Некорректная дата выхода фильма";
            log.debug(message);
            throw new ValidationException(message);
        }
    }

}
