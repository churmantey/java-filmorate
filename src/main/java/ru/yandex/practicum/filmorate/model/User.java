package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Integer id;

    @NotNull
    @Pattern(regexp = "(\\w)+", message = "Логин не дожен быть пустым и содержать пробелы или спецсимволы")
    private String login;

    private String name;

    @NotBlank
    @Email
    private String email;

    @PastOrPresent
    private LocalDate birthday;

    public User(Integer id, String login, String name, String email, LocalDate birthday) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        if (this.name == null || this.name.isBlank()) {
            this.name = this.login;
        }
    }

}
