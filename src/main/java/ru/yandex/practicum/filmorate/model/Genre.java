package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Genre {
    @NotNull
    Integer id;

    @NotBlank
    String name;

    String description;
}
