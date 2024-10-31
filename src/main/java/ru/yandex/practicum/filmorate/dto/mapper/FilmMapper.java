package ru.yandex.practicum.filmorate.dto.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;

@NoArgsConstructor
public final class FilmMapper {

    public static Film mapToFilm(NewFilmRequest request) {
        Film film = new Film(
                request.getName(),
                request.getDescription(),
                request.getReleaseDate(),
                request.getDuration(),
                request.getMpa().getId()
        );
        if (request.getGenres() != null) {
            film.getGenres().addAll(request.getGenres().stream()
                    .map(idEntity -> new Genre(idEntity.getId(), idEntity.getName()))
                    .sorted(Comparator.comparing(Genre::getId))
                    .toList());
        }
        if (request.getDirectors() != null) {
            film.getDirectors().addAll(request.getDirectors().stream()
                    .map(DirectorMapper::mapToDirector)
                    .sorted(Comparator.comparing(Director::getId))
                    .toList());
        }
        return film;
    }

    public static Film mapToFilm(UpdateFilmRequest request) {
        Film film = mapToFilm((NewFilmRequest) request);
        film.setId(request.getId());
        return film;
    }

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setDuration(film.getDuration());
        if (film.getMpa() != null) {
            filmDto.setMpa(new IdEntity(film.getMpa().getId(), film.getMpa().getName()));
        }
        if (film.getGenres() != null) {
            filmDto.getGenres().addAll(
                    film.getGenres().stream()
                            .map(genre -> new IdEntity(genre.getId(), genre.getName()))
                            .sorted(Comparator.comparing(IdEntity::getId))
                            .toList());
        }
        if (film.getDirectors() != null) {
            filmDto.getDirectors().addAll(
                    film.getDirectors().stream()
                            .map(DirectorMapper::mapToDirectorDto)
                            .sorted(Comparator.comparing(DirectorDto::getId))
                            .toList()
            );
        }
        return filmDto;
    }

}
