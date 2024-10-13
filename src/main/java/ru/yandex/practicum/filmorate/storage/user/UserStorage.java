package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

public interface UserStorage extends BaseStorage<User> {

    List<User> getMutualFriends(Integer userId, Integer otherUserId);

    User addUserFriend(Integer userId, Integer friendId);

    User removeUserFriend(Integer userId, Integer friendId);

}
