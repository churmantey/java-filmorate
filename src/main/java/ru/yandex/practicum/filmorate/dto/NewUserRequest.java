package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class NewUserRequest {
    @NotBlank
    private String login;

    @Email
    private String email;

    @PastOrPresent
    private LocalDate birthday;

    private String name;
}
