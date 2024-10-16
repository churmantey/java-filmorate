package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
    @NotNull
    private Integer id;

    private String name;
    private String description;

    public Rating(Integer id) {
        this.id = id;
        this.name = "";
        this.description = "";
    }
}
