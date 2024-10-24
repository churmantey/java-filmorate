package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.Update;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Review {
    @NotNull(groups = {Update.class})
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String content;

    @NotNull
    private boolean isPositive;

    @NotNull
    private Integer userId;
    @NotNull
    private Integer filmId;

    private Integer useful;
}
