package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Set;

public interface DirectorService {
    List<Director> getAll();

    List<Director> getAllDirectorForOneFilm(Integer id);

    Director createDirector(Director director);

    Director getDirector(Integer id);

    Director updateDirector(Director director);

    boolean deleteDirector(Integer id);

    List<Director> getDirectorByIds(Set<Integer> directorIds);

    Set<Integer> getDirectorsIdsOfFilm(Integer id);

    void insertFilmAndDirector(Integer film_id, Set<Integer> directorIds);

}


