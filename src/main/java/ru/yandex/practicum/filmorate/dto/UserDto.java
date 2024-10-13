package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
    private final Set<IdEntity> friends;

    public UserDto() {
        this.friends = new LinkedHashSet<>();
    }
}
