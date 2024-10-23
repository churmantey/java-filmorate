package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    FilmDto getFilmById(Integer filmId);

    FilmDto createFilm(NewFilmRequest newFilmRequest);

    FilmDto updateFilm(UpdateFilmRequest updateFilmRequest);

    boolean deleteFilm(Film film);

    boolean deleteFilmById(Integer id);

    List<FilmDto> getAllFilms();

    List<FilmDto> getSortedFilms(Integer directorId, String sort);
}
