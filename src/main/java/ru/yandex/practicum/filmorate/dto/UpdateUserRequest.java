package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class UpdateUserRequest extends NewUserRequest {
    @NotNull
    @Positive
    private Integer id;

}
