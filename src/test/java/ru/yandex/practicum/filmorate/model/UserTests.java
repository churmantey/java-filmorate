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

        assertEquals(1, violations.size(), "Неожиданное количество нарушений при валидации");
        assertEquals("Логин не дожен быть пустым и содержать пробелы или спецсимволы",
                violations.stream().findFirst().get().getMessage());
    }

}
