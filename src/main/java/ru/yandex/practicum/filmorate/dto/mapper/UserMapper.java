package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    default User mapNewRequestToUserCheck(NewUserRequest userRequest) {
        if (userRequest.getName() == null || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }
        return mapNewRequestToUser(userRequest);
    }

    default User mapUpdateRequestToUserCheck(UpdateUserRequest userRequest) {
        if (userRequest.getName() == null || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }
        return mapUpdateUserRequestToUser(userRequest);
    }

    User mapNewRequestToUser(NewUserRequest userRequest);

    User mapUpdateUserRequestToUser(UpdateUserRequest userRequest);

    UserDto mapUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);

    List<UserDto> mapUserListToUserDtoList(List<User> employees);

}