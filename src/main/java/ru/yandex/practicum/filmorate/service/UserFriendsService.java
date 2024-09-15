package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFriendsService {

    private final UserService userService;

    public List<User> getMutualFriends(Integer userId, Integer otherUserId) {
        User user = userService.getUserById(userId);
        User otherUser = userService.getUserById(otherUserId);
        return user.getFriends().stream()
                .filter(friendId -> (otherUser.getFriends().contains(friendId)))
                .map(userService::getUserById)
                .toList();
    }

    public List<User> getUserFriends(Integer userId) {
        User user = userService.getUserById(userId);
        return user.getFriends().stream()
                .map(userService::getUserById)
                .toList();
    }

    public User addFriend(Integer userId, Integer friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
        return user;
    }

    public User removeFriend(Integer userId, Integer friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
        return user;
    }

}
