package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserFriendsService {

    List<User> getMutualFriends(Integer userId, Integer otherUserId);

    List<User> getUserFriends(Integer userId);

    User addFriend(Integer userId, Integer friendId);

    User removeFriend(Integer userId, Integer friendId);

}
