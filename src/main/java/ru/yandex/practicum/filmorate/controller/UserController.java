package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.IdGenerator;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final IdGenerator idGenerator;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
        this.idGenerator = new IdGenerator();
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.debug("GET users");
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("POST user {}", user);
        user.setId(idGenerator.getNextId());
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.debug("PUT user {}", newUser);
        return userService.updateUser(newUser);
    }

    //PUT /users/{id}/friends/{friendId}
    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id,
                          @PathVariable Integer friendId) {
        log.debug("PUT add friend {} for user {}", friendId, id);
        return userService.addFriend(id, friendId);
    }

    //DELETE /users/{id}/friends/{friendId}
    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        log.debug("DELETE friend {} from user {} ", friendId, id);
        return userService.removeFriend(id, friendId);
    }

    //GET /users/{id}/friends
    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable Integer id) {
        log.debug("GET user {} friends", id);
        return userService.getUserFriends(id);
    }

    //GET /users/{id}/friends/common/{otherId}
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Integer id,
                                       @PathVariable Integer otherId) {
        log.debug("GET mutual friends of user {} and user {}", id, otherId);
        return userService.getMutualFriends(id, otherId);
    }
}
