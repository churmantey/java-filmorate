package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmTests {

//    название не может быть пустым;
//    максимальная длина описания — 200 символов;
//    дата релиза — не раньше 28 декабря 1895 года;
//    продолжительность фильма должна быть положительным числом.
    @Test
    public void whenNameIsEmptyThenNotValid () {

        Film film = new Film(1,
                "",
                "dssdfsds dsdf",
                LocalDate.of(1970, 12, 1),
                50);

        assertThrows(ValidationException.class, film::validate);

    }

    @Test
    public void whenDescriptionIsBigThenNotValid () {

        Film film = new Film(1,
                "gerg ergewrgewger",
                "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567891",
                LocalDate.of(1970, 12, 1),
                50);

        assertThrows(ValidationException.class, film::validate);

    }

    @Test
    public void whenDescriptionLengthIs200ThenValid () {

        Film film = new Film(1,
                "gerg ergewrgewger",
                "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789012345678901234567890123456789"
                        + "0123456789012345678901234567890123456789",
                LocalDate.of(1970, 12, 1),
                50);

        assertDoesNotThrow(film::validate);

    }

    @Test
    public void whenReleaseDateBeforeCinemaEpochThenNotValid () {

        Film film = new Film(1,
                "gerg ergewrgewger",
                "ert tue56 uw347",
                LocalDate.of(1895, 12, 27),
                50);

        assertThrows(ValidationException.class, film::validate);

    }

    @Test
    public void whenReleaseDateAfterCinemaEpochThenValid () {

        Film film = new Film(1,
                "gerg ergewrgewger",
                "werwer werw",
                LocalDate.of(1895, 12, 28),
                50);

        assertDoesNotThrow(film::validate);

    }

    @Test
    public void whenDurationZeroThenNotValid () {

        Film film = new Film(1,
                "gerg ergewrgewger",
                "werwer werw",
                LocalDate.of(1950, 12, 28),
                0);

        assertThrows(ValidationException.class, film::validate);

    }

}
