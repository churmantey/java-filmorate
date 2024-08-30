package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    Integer id;
    @Pattern(regexp = "(\\w)+", message = "Логин не дожен быть пустым и содержать пробелы или спецсимволы")
    String login;
    String name;
    @Email
    String email;
    @Past
    LocalDate birthday;

    public void checkName() {
        if (this.getName() == null || this.getName().isBlank()) {
            this.setName(this.getLogin());
        }
    }

}
