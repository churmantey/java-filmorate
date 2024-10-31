package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DirectorDto {
    private Integer id;

    @NotBlank
    @Size(message = "Имя режиссера не может быть длиннее 200 символов", max = 200)
    private String name;

    public DirectorDto(Integer id) {
        this.id = id;
    }
}
