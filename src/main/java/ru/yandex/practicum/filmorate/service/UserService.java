package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> getMutualFriends(Integer userId1, Integer userId2) {
        User user1 = getUserById(userId1);
        User user2 = getUserById(userId2);
        return user1.getFriends().stream()
                .filter(friendId -> (user2.getFriends().contains(friendId)))
                .map(userStorage::getElement)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<User> getUserFriends(Integer userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()
                .map(userStorage::getElement)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public User addFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
        return user;
    }

    public User removeFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
        return user;
    }

    private User getUserById(Integer userId) {
        if (userId == null) {
            throw new NullUserException("Передан id пользователя null");
        }
        Optional<User> maybeUser = userStorage.getElement(userId);
        if (maybeUser.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        return maybeUser.get();
    }

    public User createUser(User user) {
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
