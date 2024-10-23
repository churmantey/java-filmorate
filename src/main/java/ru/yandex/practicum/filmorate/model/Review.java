package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Review {
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String content;

    private boolean isPositive;

    @NotNull
    private Long userId;
    private Long filmId;

    private int useful;
}
