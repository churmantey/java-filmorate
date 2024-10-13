package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {

    private final UserStorage userStorage;

    public UserFriendsServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<UserDto> getMutualFriends(Integer userId, Integer otherUserId) {
        List<User> uLst = userStorage.getMutualFriends(userId, otherUserId);
        return uLst.stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getUserFriends(Integer userId) {
        User user = userStorage.getElement(userId);
        return user.getFriends().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    public UserDto addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getElement(userId);
        User friend = userStorage.getElement(friendId);

        return UserMapper.mapToUserDto(
                userStorage.addUserFriend(userId, friendId)
        );
    }

    @Override
    public UserDto removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getElement(userId);
        User friend = userStorage.getElement(friendId);
        if (user.getFriends().contains(friend)) {
            return UserMapper.mapToUserDto(
                    userStorage.removeUserFriend(userId, friendId)
            );
        } else {
            return UserMapper.mapToUserDto(user);
        }
    }

}
