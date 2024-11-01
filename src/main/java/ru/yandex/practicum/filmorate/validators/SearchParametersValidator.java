package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchParametersValidator implements
        ConstraintValidator<SearchParametersConstraint, String> {

    private final Set<String> validCriterions = new LinkedHashSet<>() {{
        add("director");
        add("title");
        add("director,title");
        add("title,director");
    }};

    @Override
    public void initialize(SearchParametersConstraint input) {

    }

    @Override
    public boolean isValid(String inputParam,
                           ConstraintValidatorContext cxt) {
        if (inputParam == null)
            return false;
        String workStr = inputParam.replaceAll(" ", "").toLowerCase();
        return validCriterions.contains(workStr);
    }
}
