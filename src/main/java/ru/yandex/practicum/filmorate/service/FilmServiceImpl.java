package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.dto.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.Collection;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final RatingStorage ratingStorage;
    private final GenreStorage genreStorage;

    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                           RatingStorage ratingStorage,
                           GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.ratingStorage = ratingStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public FilmDto getFilmById(Integer filmId) {
        if (filmId == null) {
            throw new NullObjectException("Передан id фильма null");
        }
        Film film = filmStorage.getElement(filmId);
        if (film == null) {
            throw new NotFoundException("Не найден фильм с id = " + filmId);
        }
        return FilmMapper.mapToFilmDto(film);
    }

    @Override
    public FilmDto createFilm(NewFilmRequest newFilmRequest) {
        validateRating(newFilmRequest.getMpa());
        validateGenres(newFilmRequest.getGenres());
        Film film = FilmMapper.mapToFilm(newFilmRequest);
        film.validate();
        return FilmMapper.mapToFilmDto(
                filmStorage.addElement(film)
        );
    }

    @Override
    public FilmDto updateFilm(UpdateFilmRequest updateFilmRequest) {
        validateRating(updateFilmRequest.getMpa());
        validateGenres(updateFilmRequest.getGenres());
        Film film = FilmMapper.mapToFilm(updateFilmRequest);
        film.validate();
        Film oldFilm = filmStorage.getElement(film.getId());
        return FilmMapper.mapToFilmDto(
                filmStorage.updateElement(film)
        );
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
    public List<FilmDto> getAllFilms() {
        return filmStorage.getAllElements().stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    @Override
    public void validateRating(Rating rating) {
        if (rating == null
                || !ratingStorage.isValidRatingId(rating.getId())) {
            throw new ValidationException("Рейтинг фильма не указан или не найден.");
        }
    }

    @Override
    public void validateGenres(Collection<Genre> genreList) {
        if (genreList != null) {
            for (Genre genre : genreList) {
                if (!genreStorage.isValidGenreId(genre.getId())) {
                    throw new ValidationException("Жанр фильма с id = " + genre.getId() + " не указан или не найден.");
                }
            }
        }
    }
}
