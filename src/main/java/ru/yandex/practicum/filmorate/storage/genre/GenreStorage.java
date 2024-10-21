package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.Set;

public interface GenreStorage extends BaseStorage<Genre> {

    Set<Genre> getFilmGenresById(Integer filmId);

    void deleteFilmGenresById(Integer filmId);

    void updateFilmGenresById(Integer filmId, Set<Genre> genreList);

    boolean isValidGenreId(Integer id);

}
