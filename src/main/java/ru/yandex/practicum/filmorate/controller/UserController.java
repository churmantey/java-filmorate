package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.EventService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("GET users");
        List<UserDto> userList = userService.getAllUsers();
        log.info("GET users RESPONSE {}", userList);
        return userList;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) {
        log.info("GET user {}", userId);
        UserDto user = userService.getUserById(userId);
        log.info("GET user RESPONSE {}", user);
        return user;
    }

    @GetMapping("/{userId}/feed")
    public List<Event> getEvents(@PathVariable Integer userId) {
        log.info("Пришел GET /users/{}/feed", userId);
        List<Event> events = eventService.getEvents(userId);
        log.info("Отправлен ответ длины {}", events.size());
        return events;
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("POST user {}", newUserRequest);
        UserDto userDto = userService.createUser(newUserRequest);
        log.info("Was created user {}", userDto);
        return userDto;
    }

    @PutMapping
    public UserDto updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("PUT user {}", updateUserRequest);
        UserDto userDto = userService.updateUser(updateUserRequest);
        log.info("Was updated user {}", userDto);
        return userDto;
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUserById(@PathVariable Integer userId) {
        log.info("DELETE user {}", userId);
        UserDto user = userService.getUserById(userId);
        boolean result = userService.deleteUserById(user.getId());
        log.info("Was deleted user {}", userId);
        return result;
    }
}
