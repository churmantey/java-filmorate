package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.DirectorDto;

import java.util.List;
import java.util.Set;

public interface DirectorService {
    List<DirectorDto> getAllDirectors();

    List<DirectorDto> getAllDirectorForOneFilm(Integer id);

    DirectorDto createDirector(DirectorDto directorDto);

    DirectorDto getDirector(Integer id);

    DirectorDto updateDirector(DirectorDto directorDto);

    boolean deleteDirector(Integer id);

    List<DirectorDto> getDirectorByIds(Set<Integer> directorIds);

    Set<Integer> getDirectorsIdsOfFilm(Integer id);

    void insertFilmAndDirector(Integer filmId, Set<Integer> directorIds);

    void deleteFilmsAndDirectors(Integer filmId);

}


