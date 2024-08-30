package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    public static final LocalDate CINEMA_EPOCH = LocalDate.of(1895, 12, 28);

    private Integer id;

    @NotBlank
    private String name;

    @Size(min = 0, max = 200)
    private String description;

    @PastOrPresent
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    public void validate() {
        String message = "";
        if (this.getReleaseDate().isBefore(CINEMA_EPOCH)) {
            message = "Некорректная дата выхода фильма";
        } else if (this.getName().isBlank()) {
            message = "Название фильма не заполнено";
        } else if (this.getDescription().length() > 200) {
            message = "Слишком длинное описание фильма";
        } else if (this.duration <= 0 ) {
            message = "Длительность фильма должна быть положительным числом";
        }
        if (!message.isBlank()) {
            log.debug(message);
            throw new ValidationException(message);
        }
    }

}
