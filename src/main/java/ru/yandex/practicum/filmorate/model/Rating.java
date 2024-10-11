package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @NotNull
    Integer id;

    String name;
    String description;

    public Rating(Integer id) {
        this.id = id;
        this.name = "";
        this.description = "";
    }
}
