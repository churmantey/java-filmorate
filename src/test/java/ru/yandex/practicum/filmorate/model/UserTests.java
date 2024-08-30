package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    ValidatorFactory factory = buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void whenIncorrectLoginThenNotValid() {
        User user = new User(1, "rt yeryt", "saa fff", "ddd@www.ru",
                LocalDate.of(1975, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации плохого логина");
        assertEquals("Логин не дожен быть пустым и содержать пробелы или спецсимволы",
                violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void whenEmptyLoginThenNotValid() {
        User user = new User(1, "", "saa fff", "ddd@www.ru",
                LocalDate.of(1975, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации пустого логина");
        assertEquals("Логин не дожен быть пустым и содержать пробелы или спецсимволы",
                violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void whenNullLoginThenNotValid() {
        User user = new User(1, null, "saa fff", "ddd@www.ru",
                LocalDate.of(1975, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации null login");
    }

    @Test
    public void whenEmptyMailThenNotValid() {
        User user = new User(1, "darius", "saa fff", null,
                LocalDate.of(1975, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации null email");
        user = new User(1, "darius", "saa fff", "",
                LocalDate.of(1975, 12, 1));
        violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации пустого email");
    }

    @Test
    public void whenMalformedMailThenNotValid() {
        User user = new User(1, "darius", "saa fff", "@erert_rr",
                LocalDate.of(1975, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации плохого email");
    }

    @Test
    public void whenUserIsGuestFromFutureThenNotValid() {
        User user = new User(1, "Alice", "Alice Seleznyova", "i@am.here",
                LocalDate.of(2080, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации даты рождения");
    }

    @Test
    public void whenEmptyNameThenNameFromLogin() {
        User user = new User(1, "Alice", "", "i@am.here",
                LocalDate.of(2020, 12, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(),
                "Неожиданное количество нарушений при валидации пустого имени");
        assertEquals(user.getLogin(), user.getName());
    }

}
