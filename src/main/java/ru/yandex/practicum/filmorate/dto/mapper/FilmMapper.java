package ru.yandex.practicum.filmorate.dto.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.IdEntity;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

@NoArgsConstructor
public final class FilmMapper {

    public static Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        if (request.getMpa() != null) {
            film.setMpa(request.getMpa());
        }
        if (request.getGenres() != null) {
            film.getGenres().addAll(request.getGenres());
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
        if (film.getLikes() != null) {
            filmDto.getLikes().addAll(
                    film.getLikes().stream()
                            .map(IdEntity::new)
                            .toList()
            );
        }
        if (film.getMpa() != null) {
            filmDto.setMpa(new IdEntity(film.getMpa().getId()));
        }
        if (film.getGenres() != null) {
            filmDto.getGenres().addAll(
                    film.getGenres().stream()
                    .map(genre -> new IdEntity(genre.getId()))
                    .toList());
        }
        return filmDto;
    }

}
