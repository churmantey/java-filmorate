package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

public class FilmTests {

    private final ValidatorFactory factory = buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private Set<ConstraintViolation<Film>> violations;
    private Film film;

    @BeforeEach
    public void setUp() {
        film = new Film(1,
                "Test film name",
                "Test film description",
                LocalDate.of(1970, 12, 1),
                50);
    }

    //    название не может быть пустым;
    //    максимальная длина описания — 200 символов;
    //    дата релиза — не раньше 28 декабря 1895 года;
    //    продолжительность фильма должна быть положительным числом.
    @Test
    public void whenNameIsEmptyThenNotValid() {
        film.setName("");
        violations = validator.validate(film);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации пустого названия");
        film.setName(null);
        violations = validator.validate(film);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации null названия");
    }

    @Test
    public void whenDescriptionIsBigThenNotValid() {
        film.setDescription("01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                + "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                + "01234567890123456789012345678901234567891");
        violations = validator.validate(film);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации большого описания");
    }

    @Test
    public void whenDescriptionLengthIs200ThenValid() {
        film.setDescription("01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                + "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                + "0123456789012345678901234567890123456789");
        violations = validator.validate(film);
        assertEquals(0, violations.size(),
                "Неожиданное количество нарушений при валидации корректного описания");
    }

    @Test
    public void whenReleaseDateBeforeCinemaEpochThenNotValid() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, film::validate);
    }

    @Test
    public void whenReleaseDateAfterCinemaEpochThenValid() {
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        assertDoesNotThrow(film::validate);
    }

    @Test
    public void whenDurationZeroThenNotValid() {
        film.setDuration(0);
        violations = validator.validate(film);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации нулевой длительности");
    }

}
