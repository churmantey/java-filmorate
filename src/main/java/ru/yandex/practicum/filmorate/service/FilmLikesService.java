package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.util.List;

public interface FilmLikesService {

    FilmDto addLike(Integer filmId, Integer userId);

    FilmDto removeLike(Integer filmId, Integer userId);

    List<FilmDto> getPopular(int count);

    List<FilmDto> getPopularFilmsByYearAndGenre(Integer count,Integer genreId, Integer year);

}
