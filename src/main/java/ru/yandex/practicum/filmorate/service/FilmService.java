package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.List;

public interface FilmService {

    FilmDto getFilmById(Integer filmId);

    FilmDto createFilm(NewFilmRequest newFilmRequest);

    FilmDto updateFilm(UpdateFilmRequest updateFilmRequest);

    boolean deleteFilm(Film film);

    boolean deleteFilmById(Integer id);

    List<FilmDto> getAllFilms();

    void validateRating (Rating rating);

    void validateGenres (Collection<Genre> genreList);

    public List<FilmDto> getTopRatedFilms(int count);
}
