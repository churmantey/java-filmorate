package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final IdGenerator idGenerator;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
        this.idGenerator = new IdGenerator();
    }


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

    public User createUser(User user) {
        user.setId(idGenerator.getNextId());
        return userStorage.addElement(user);
    }

    public User updateUser(User user) {
        return userStorage.updateElement(user);
    }

    public User deleteUser(User user) {
        return userStorage.deleteElement(user);
    }

    public User deleteUserById(Integer id) {
        return userStorage.deleteElementById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllElements();
    }
}
