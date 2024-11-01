package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.util.List;

public interface UserRecommendationsService {

    List<FilmDto> getRecommendedFilms(Integer userId);
}
