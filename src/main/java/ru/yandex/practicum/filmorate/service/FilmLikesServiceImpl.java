package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class FilmLikesServiceImpl implements FilmLikesService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmLikesServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                                @Qualifier("userDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public FilmDto addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getElement(filmId);
        User user = userStorage.getElement(userId);
        filmStorage.addLike(filmId, userId);
        film.getLikes().add(user);
        return FilmMapper.mapToFilmDto(film);
    }

    @Override
    public FilmDto removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getElement(filmId);
        User user = userStorage.getElement(userId);
        if (film.getLikes().contains(user)) {
            filmStorage.removeLike(filmId, userId);
        }
        return FilmMapper.mapToFilmDto(
                filmStorage.getElement(filmId)
        );
    }

    @Override
    public List<FilmDto> getTopRatedFilms(int count) {
        return filmStorage.getTopRatedFilms(count).stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

}
