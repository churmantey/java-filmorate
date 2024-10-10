package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.Set;

public interface GenreStorage extends BaseStorage<Genre> {

    public Set<Genre> getFilmGenresById(Integer filmId);

}
