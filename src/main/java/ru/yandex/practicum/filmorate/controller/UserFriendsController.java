package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserFriendsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserFriendsController {

    private final UserFriendsService userFriendsService;

    //PUT /users/{id}/friends/{friendId}
    @PutMapping("/{id}/friends/{friendId}")
    public UserDto addFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        log.info("PUT add friend {} for user {}", friendId, id);
        return userFriendsService.addFriend(id, friendId);
    }

    //DELETE /users/{id}/friends/{friendId}
    @DeleteMapping("/{id}/friends/{friendId}")
    public UserDto removeFriend(@PathVariable Integer id,
                                @PathVariable Integer friendId) {
        log.info("DELETE friend {} from user {} ", friendId, id);
        return userFriendsService.removeFriend(id, friendId);
    }

    //GET /users/{id}/friends
    @GetMapping("/{id}/friends")
    public List<UserDto> getUserFriends(@PathVariable Integer id) {
        log.info("GET user {} friends", id);
        return userFriendsService.getUserFriends(id);
    }

    //GET /users/{id}/friends/common/{otherId}
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable Integer id,
                                          @PathVariable Integer otherId) {
        log.info("GET mutual friends of user {} and user {}", id, otherId);
        return userFriendsService.getCommonFriends(id, otherId);
    }
}
