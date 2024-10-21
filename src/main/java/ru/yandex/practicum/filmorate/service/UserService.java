package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    UserDto updateUser(UpdateUserRequest updateUserRequest);

    boolean deleteUser(User user);

    boolean deleteUserById(Integer id);

    List<UserDto> getAllUsers();

    UserDto getUserById(Integer userId);

}
