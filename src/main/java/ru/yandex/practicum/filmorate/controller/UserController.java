package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("GET users");
        List<UserDto> userList = userService.getAllUsers();
        log.info("GET users RESPONSE {}", userList);
        return userList;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId) {
        log.info("GET user {}", userId);
        UserDto user = userService.getUserById(userId);
        log.info("GET user RESPONSE {}", user);
        return user;
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("POST user {}", newUserRequest);
        return userService.createUser(newUserRequest);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("PUT user {}", updateUserRequest);
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping("/{userId}")
    public boolean delete(@PathVariable Integer userId) {
        log.info("DELETE user {}", userId);
        UserDto user = userService.getUserById(userId);
        return userService.deleteUserById(user.getId());
    }
}
