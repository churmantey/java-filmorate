package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

public interface FilmStorage extends BaseStorage<Film> {

    List<Film> getTopRatedFilms(int count);

    void addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);

    List<Integer> getFilmLikes(Integer filmId);

    List<Integer> getFilmsLikesByUsers(Integer userId);

}