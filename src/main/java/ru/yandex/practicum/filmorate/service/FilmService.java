package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullFilmException;
import ru.yandex.practicum.filmorate.exception.NullUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = getUserById(userId);
        film.getLikes().add(user.getId());
        return film;
    }

    public Film removeLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = getUserById(userId);
        film.getLikes().remove(user.getId());
        return film;
    }

    public Film getFilmById(Integer filmId) {
        if (filmId == null) {
            throw new NullFilmException("Передан id фильма null");
        }
        Optional<Film> maybeFilm = filmStorage.getElement(filmId);
        if (maybeFilm.isEmpty()) {
            throw new NotFoundException("Не найден фильм с id = " + filmId);
        }
        return maybeFilm.get();
    }

    private User getUserById(Integer userId) {
        if (userId == null) {
            throw new NullUserException("Передан id пользователя null");
        }
        Optional<User> maybeUser = userStorage.getElement(userId);
        if (maybeUser.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + userId);
        }
        return maybeUser.get();
    }

    public List<Film> getTopRatedFilms(int count) {
        return filmStorage.getAllElements().stream()
                .sorted(new FilmLikesComparator().reversed())
                .limit(count)
                .toList();
    }

    public Film createFilm(Film film) {
        return filmStorage.addElement(film);
    }

    public Film updateFilm(Film film) {
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
