package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean deleteElement(User element) {
        if (element == null) {
            throw new NullObjectException("Не найден пользователь null");
        }
        if (storage.containsKey(element.getId())) {
            storage.remove(element.getId());
            return true;
        }
        throw new NotFoundException("Не найден пользователь с id = " + element.getId());
    }

    @Override
    public boolean deleteElementById(Integer id) {
        if (id == null) {
            throw new NullObjectException("Не найден пользователь с id null");
        }
        if (storage.containsKey(id)) {
            storage.remove(id);
            return true;
        }
        throw new NotFoundException("Не найден пользователь с id = " + id);
    }

    @Override
    public User updateElement(User newElement) {
        if (newElement == null) {
            throw new NullObjectException("Не найден пользователь null");
        }
        if (storage.containsKey(newElement.getId())) {
            User oldUser = storage.get(newElement.getId());
            oldUser.setLogin(newElement.getLogin());
            oldUser.setName(newElement.getName());
            oldUser.setBirthday(newElement.getBirthday());
            oldUser.setEmail(newElement.getEmail());
            return oldUser;
        }
        throw new NotFoundException("Не найден пользователь с id = " + newElement.getId());
    }

    @Override
    public List<User> getAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User getElement(Integer id) {
        return storage.get(id);
    }
}
