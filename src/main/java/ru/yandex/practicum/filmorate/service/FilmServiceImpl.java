package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.SearchParams;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.dto.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NullObjectException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final RatingStorage ratingStorage;
    private final GenreStorage genreStorage;
    private final DirectorService directorService;

    @Override
    public FilmDto getFilmById(Integer filmId) {
        if (filmId == null) {
            throw new NullObjectException("Передан id фильма null");
        }
        Film film = filmStorage.getElement(filmId);
        if (film == null) {
            throw new NotFoundException("Не найден фильм с id = " + filmId);
        }

        Set<Integer> directorsIds = directorService.getDirectorsIdsOfFilm(filmId);
        List<Director> directors = directorService.getDirectorByIds(directorsIds);
        film.getDirectors().addAll(new LinkedHashSet<>(directors));

        return FilmMapper.mapToFilmDto(film);
    }

    @Override
    public FilmDto createFilm(NewFilmRequest newFilmRequest) {
        validateRating(newFilmRequest.getMpa());
        validateGenres(newFilmRequest.getGenres());
        Set<Integer> directorsIds = validateDirectors(newFilmRequest);


        Film film = FilmMapper.mapToFilm(newFilmRequest);
        film.validate();
        Film newFilm = filmStorage.addElement(film);

        directorService.insertFilmAndDirector(newFilm.getId(), directorsIds);
        return FilmMapper.mapToFilmDto(newFilm);
    }

    @Override
    public FilmDto updateFilm(UpdateFilmRequest updateFilmRequest) {
        validateRating(updateFilmRequest.getMpa());
        validateGenres(updateFilmRequest.getGenres());
        Set<Integer> directorsIds = validateDirectors(updateFilmRequest);

        Film film = FilmMapper.mapToFilm(updateFilmRequest);
        film.validate();
        Film oldFilm = filmStorage.getElement(film.getId());
        directorService.insertFilmAndDirector(film.getId(), directorsIds);

        return FilmMapper.mapToFilmDto(
                filmStorage.updateElement(film)
        );
    }

    @Override
    public boolean deleteFilm(Film film) {
        return filmStorage.deleteElement(film);
    }

    @Override
    public boolean deleteFilmById(Integer id) {
        return filmStorage.deleteElementById(id);
    }


    @Override
    public List<FilmDto> getAllFilms() {
        return filmStorage.getAllElements().stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    @Override
    public List<FilmDto> getSortedFilms(Integer directorId, String sort) {
        List<Film> films;
        if (sort.equalsIgnoreCase("year")) {
            films = filmStorage.getSortedFilmsByYear(directorId);
        } else if (sort.equalsIgnoreCase("likes")) {
            films = filmStorage.getSortedFilmsByLikes(directorId);
        } else {
            throw new ValidationException("Введен некорректный фильтр сортировки");
        }

        System.out.println(films);
        return films.stream()
                .peek(film -> film.getDirectors()
                        .addAll(new LinkedHashSet<>(directorService.getAllDirectorForOneFilm(film.getId())))
                )
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    private void validateRating(Rating rating) {
        if (rating == null
                || !ratingStorage.isValidRatingId(rating.getId())) {
            throw new ValidationException("Рейтинг фильма не указан или не найден.");
        }
    }

    private void validateGenres(Collection<Genre> genreList) {
        if (genreList != null) {
            for (Genre genre : genreList) {
                if (!genreStorage.isValidGenreId(genre.getId())) {
                    throw new ValidationException("Жанр фильма с id = " + genre.getId() + " не указан или не найден.");
                }
            }
        }
    }

    private Set<Integer> validateDirectors(NewFilmRequest newFilmRequest) {
        if (newFilmRequest.getDirectors() != null) {
            Set<Integer> directorsIds = newFilmRequest.getDirectors().stream()
                    .map(Director::getId)
                    .collect(Collectors.toSet());
            List<Director> directors = directorService.getDirectorByIds(directorsIds);
            if (newFilmRequest.getDirectors().size() != directors.size()) {
                throw new ValidationException("Введен некорректный режиссер");
            }
            newFilmRequest.setDirectors(new LinkedHashSet<>(directors));
            return directorsIds;
        }
        return new LinkedHashSet<>();
    }

    public List<FilmDto> getCommonFilmsLikesByUsers(Integer userId, Integer friendId) {
        return filmStorage.getFilmsLikesByUsers(userId, friendId).stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    @Override
    public List<FilmDto> getFilmsByContext(String query, String criterion) {
        SearchParams searchParams = new SearchParams(query, criterion);
        return filmStorage.getByContext(searchParams).stream()
                .map(FilmMapper::mapToFilmDto)
                .sorted(Comparator.comparing(FilmDto::getName))
                .toList();
    }

}
