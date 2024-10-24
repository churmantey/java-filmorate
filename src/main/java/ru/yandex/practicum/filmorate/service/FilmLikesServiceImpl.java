package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.exception.DatabaseOperationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmLikesServiceImpl implements FilmLikesService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;

    @Override
    public FilmDto addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getElement(filmId);
        User user = userStorage.getElement(userId);
        filmStorage.addLike(filmId, userId);
        return FilmMapper.mapToFilmDto(film);
    }

    @Override
    public FilmDto removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getElement(filmId);
        User user = userStorage.getElement(userId);
        filmStorage.removeLike(filmId, userId);
        return FilmMapper.mapToFilmDto(
                filmStorage.getElement(filmId)
        );
    }

    @Override
    public List<FilmDto> getPopular(int count) {
        return filmStorage.getTopRatedFilms(count).stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    @Override
    public List<FilmDto> getPopularFilmsByYearAndGenre(Integer count, Integer genreId, Integer year) {
        Genre genre;
        if (genreId != null) {
            genre = genreStorage.getElement(genreId);
        } else {
            genre = null;
        }
        return new java.util.ArrayList<>(filmStorage.getAllTopRatedFilms().stream()
                .filter(film -> genre == null || film.getGenres().contains(genre))
                .filter(film -> year == null || film.getReleaseDate().getYear() == year)
                .limit(count)
                .map(FilmMapper::mapToFilmDto)
                .toList());
    }
}
