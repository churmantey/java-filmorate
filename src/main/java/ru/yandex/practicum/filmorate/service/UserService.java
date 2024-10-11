package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(User user);

    UserDto updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(Integer id);

    List<UserDto> getAllUsers();

    UserDto getUserById(Integer userId);

}
