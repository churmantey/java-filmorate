package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(Integer id);

    List<User> getAllUsers();

    User getUserById(Integer userId);

}
