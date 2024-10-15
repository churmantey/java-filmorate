package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class UserFriendsServiceImpl implements UserFriendsService {

    private final UserStorage userStorage;

    @Override
    public List<UserDto> getCommonFriends(Integer userId, Integer otherUserId) {
        List<User> uLst = userStorage.getMutualFriends(userId, otherUserId);
        return uLst.stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getUserFriends(Integer userId) {
        User user = userStorage.getElement(userId);
        return userStorage.getUserFriends(userId).stream()
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
        userStorage.removeUserFriend(userId, friendId);
        return UserMapper.mapToUserDto(user);
    }

}
