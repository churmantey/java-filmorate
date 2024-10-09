package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmDbStorage filmStorage;
    private final IdGenerator idGenerator;

    @Override
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

    @Override
    public Film createFilm(Film film) {
        film.validate();
        film.setId(idGenerator.getNextId());
        return filmStorage.addElement(film);
    }

    @Override
    public Film updateFilm(Film film) {
        film.validate();
        return filmStorage.updateElement(film);
    }

    @Override
    public boolean deleteFilm(Film film) {
        return filmStorage.deleteElement(film);
    }

    @Override
    public boolean deleteFilmById(Integer id) {
        return filmStorage.deleteElementById(id);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAllElements();
    }

}
