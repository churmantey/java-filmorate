package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> storage;

    public InMemoryFilmStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public Film addElement(Film element) {
        storage.put(element.getId(), element);
        return element;
    }

    @Override
    public Film deleteElement(Film element) {
        if (element == null) {
            throw new NullObjectException("Не найден фильм null");
        }
        if (storage.containsKey(element.getId())) {
            return storage.remove(element.getId());
        }
        throw new NotFoundException("Не найден фильм с id = " + element.getId());
    }

    @Override
    public Film deleteElementById(Integer id) {
        if (id == null) {
            throw new NullObjectException("Не найден фильм с id null");
        }
        if (storage.containsKey(id)) {
            return storage.remove(id);
        }
        throw new NotFoundException("Не найден фильм с id = " + id);
    }

    @Override
    public Film updateElement(Film newElement) {
        if (newElement == null) {
            throw new NullObjectException("Не найден фильм null");
        }
        newElement.validate();
        if (storage.containsKey(newElement.getId())) {
            Film oldFilm = storage.get(newElement.getId());
            oldFilm.setName(newElement.getName());
            oldFilm.setDescription(newElement.getDescription());
            oldFilm.setReleaseDate(newElement.getReleaseDate());
            oldFilm.setDuration(newElement.getDuration());
            return oldFilm;
        }
        throw new NotFoundException("Не найден фильм с id = " + newElement.getId());
    }

    @Override
    public List<Film> getAllElements() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Film getElement(Integer id) {
        return storage.get(id);
    }
}
