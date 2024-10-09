package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final IdGenerator idGenerator;

    @Override
    public User getUserById(Integer userId) {
        if (userId == null) {
            throw new NullObjectException("Передан id пользователя null");
        }
        User user = userStorage.getElement(userId);
        if (user == null) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        user.setId(idGenerator.getNextId());
        return userStorage.addElement(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateElement(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return userStorage.deleteElement(user);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return userStorage.deleteElementById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAllElements();
    }
}
