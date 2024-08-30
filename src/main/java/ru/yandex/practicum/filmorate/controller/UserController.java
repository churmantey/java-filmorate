package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> list() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("POST user {}", user);
        user.setId(getNextId());
        user.checkName();
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.debug("PUT user {}", newUser);
        if (users.containsKey(newUser.getId())) {
            newUser.checkName();
            User oldUser = users.get(newUser.getId());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            oldUser.setEmail(newUser.getEmail());
            return oldUser;
        }
        log.debug("User not found {}", newUser);
        throw new NotFoundException("Пользователь с id " + newUser.getId() + " не найден");
    }

    private Integer getNextId() {
        Integer currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
