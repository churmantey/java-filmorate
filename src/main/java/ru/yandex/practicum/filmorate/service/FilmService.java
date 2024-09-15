package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final IdGenerator idGenerator;


    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.idGenerator = new IdGenerator();
    }

    public Film getFilmById(Integer filmId) {
        if (filmId == null) {
            throw new NullObjectException("Передан id фильма null");
        }
        Film film = filmStorage.getElement(filmId);
        if (film == null) {
            throw new NotFoundException("Не найден фильм с id = " + filmId);
        }
        return film;
    }

    public Film createFilm(Film film) {
        film.validate();
        film.setId(idGenerator.getNextId());
        return filmStorage.addElement(film);
    }

    public Film updateFilm(Film film) {
        film.validate();
        return filmStorage.updateElement(film);
    }

    public Film deleteFilm(Film film) {
        return filmStorage.deleteElement(film);
    }

    public Film deleteFilmById(Integer id) {
        return filmStorage.deleteElementById(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllElements();
    }

}
