package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    private final ValidatorFactory factory = buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1, "userlogin", "user name", "some@email.here",
                LocalDate.of(1975, 12, 1));
    }

    @Test
    public void whenIncorrectLoginThenNotValid() {
        user.setLogin("rt yeryt");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации плохого логина");
        assertEquals("Логин не дожен быть пустым и содержать пробелы или спецсимволы",
                violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void whenEmptyLoginThenNotValid() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации пустого логина");
        assertEquals("Логин не дожен быть пустым и содержать пробелы или спецсимволы",
                violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void whenNullLoginThenNotValid() {
        user.setLogin(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации null login");
    }

    @Test
    public void whenEmptyMailThenNotValid() {
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации null email");
        user.setEmail("");
        violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации пустого email");
    }

    @Test
    public void whenMalformedMailThenNotValid() {
        user.setEmail("@erert_rr");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(),
                "Неожиданное количество нарушений при валидации плохого email");
    }

    @Test
    public void whenUserIsGuestFromFutureThenNotValid() {
        user.setBirthday(LocalDate.of(2080, 12, 1));
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
