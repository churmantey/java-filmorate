package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SearchParametersValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface SearchParametersConstraint {
    String message() default "Неверные параметры для контекстного поиска фильма";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

