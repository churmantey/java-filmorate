package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

public interface FilmStorage extends BaseStorage<Film> {

    List<Film> getTopRatedFilms(int count);

    void addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);

    List<Film> getSortedFilmsByYear(Integer id);

    List<Film> getSortedFilmsByLikes(Integer id);

    List<Integer> getFilmLikes(Integer filmId);

    List<Film> getFilmsLikesByUsers(Integer userId, Integer friendId);

    List<Film> getAllTopRatedFilms();

}