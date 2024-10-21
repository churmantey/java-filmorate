package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdEntity {
    private Integer id;
    private String name;

    public IdEntity(Integer id) {
        this.id = id;
    }
}
