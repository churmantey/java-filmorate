package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.mapper.UserMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        if (userId == null) {
            throw new NullObjectException("Передан id пользователя null");
        }
        User user = userStorage.getElement(userId);
        if (user == null) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = UserMapper.mapToUser(newUserRequest);
        return UserMapper.mapToUserDto(
                userStorage.addElement(user)
        );
    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest) {
        User newUser = UserMapper.mapToUser(updateUserRequest);
        User user = userStorage.getElement(newUser.getId());
        return UserMapper.mapToUserDto(userStorage.updateElement(newUser));
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
    public List<UserDto> getAllUsers() {
        return userStorage.getAllElements().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getMutualFriends(Integer userId, Integer otherUserId) {
        return null;
    }

}
