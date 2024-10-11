package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmLikesService {

    FilmDto addLike(Integer filmId, Integer userId);

    FilmDto removeLike(Integer filmId, Integer userId);

    List<FilmDto> getTopRatedFilms(int count);

}
