package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.Update;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Review {
    @NotNull(groups = {Update.class})
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String content;

    @NotNull
    private boolean isPositive;

    private Integer userId;
    private Integer filmId;
    private Integer useful;
}
