package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullFilmException;
import ru.yandex.practicum.filmorate.exception.NullUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> storage;

    public InMemoryUserStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public User addElement(User element) {
        storage.put(element.getId(), element);
        return element;
    }

    @Override
    public User deleteElement(User element) {
        if (element == null) {
            throw new NullFilmException("Не найден пользователь null");
        }
        if (storage.containsKey(element.getId())) {
            return storage.remove(element.getId());
        }
        throw new NotFoundException("Не найден пользователь с id = " + element.getId());
    }

    @Override
    public User deleteElementById(Integer id) {
        if (id == null) {
            throw new NullUserException("Не найден пользователь с id null");
        }
        if (storage.containsKey(id)) {
            return storage.remove(id);
        }
        throw new NotFoundException("Не найден пользователь с id = " + id);
    }

    @Override
    public User updateElement(User newElement) {
        if (storage.containsKey(newElement.getId())) {
            User oldUser = storage.get(newElement.getId());
            oldUser.setLogin(newElement.getLogin());
            oldUser.setName(newElement.getName());
            oldUser.setBirthday(newElement.getBirthday());
            oldUser.setEmail(newElement.getEmail());
            return oldUser;
        }
        return null;
    }

    @Override
    public List<User> getAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<User> getElement(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }
}
