package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapperStruct {
    UserDto mapUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);

    List<UserDto> map(List<User> employees);

}