package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.UserDto;

import java.util.List;

public interface UserFriendsService {

    List<UserDto> getCommonFriends(Integer userId, Integer otherUserId);

    List<UserDto> getUserFriends(Integer userId);

    UserDto addFriend(Integer userId, Integer friendId);

    UserDto removeFriend(Integer userId, Integer friendId);

}
