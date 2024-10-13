package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdEntity {
    private Integer id;
    private String name;

    public IdEntity(Integer id) {
        this.id = id;
    }
}
