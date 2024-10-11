package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

public interface FilmStorage extends BaseStorage<Film> {

    public List<Film> getTopRatedFilms(int count);
}
