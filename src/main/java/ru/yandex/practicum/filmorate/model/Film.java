package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

/**
 * Film.
 */

@Slf4j
@Data
@AllArgsConstructor
public class Film {

    private static final LocalDate CINEMA_EPOCH = LocalDate.of(1895, 12, 28);

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @Size(min = 0, max = 200)
    private String description;

    @PastOrPresent
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private Integer duration;

    public void validate() {
        if (this.getReleaseDate() == null || this.getReleaseDate().isBefore(CINEMA_EPOCH)) {
            String message = "Некорректная дата выхода фильма";
            log.debug(message);
            throw new ValidationException(message);
        }
    }

}
