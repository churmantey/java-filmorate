package ru.yandex.practicum.filmorate.storage.director;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;
import java.util.Set;

public interface DirectorStorage extends BaseStorage<Director> {
    List<Director> getDirectorByIds(Set<Integer> directorIds);

    Set<Integer> getDirectorIdsOfFilm(Integer id);

    void insertFilmAndDirectors(Integer film_id, Set<Integer> directorIds);

    List<Director> getDirectorsByFilmId(Integer id);
}
