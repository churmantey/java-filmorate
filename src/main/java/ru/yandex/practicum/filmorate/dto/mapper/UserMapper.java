package ru.yandex.practicum.filmorate.dto.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.IdEntity;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

@NoArgsConstructor
public final class UserMapper {

    public static User mapToUser(NewUserRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBirthday(request.getBirthday());
        return user;
    }

    public static User mapToUser(UpdateUserRequest request) {
        User user = mapToUser((NewUserRequest) request);
        user.setId(request.getId());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setBirthday(user.getBirthday());
        if (user.getFriends() != null) {
            userDto.getFriends().addAll(
                    user.getFriends().stream()
                            .map(friend -> new IdEntity(friend.getId(), friend.getName()))
                            .toList()
            );
        }
        return userDto;
    }

}
