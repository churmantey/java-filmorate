package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.*;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final RatingStorage ratingStorage;
    private final GenreStorage genreStorage;
    private final DirectorService directorService;

    @Override
    public FilmDto getFilmById(Integer filmId) {
        log.info("Passed film id = {}", filmId);
        if (filmId == null) {
            throw new NullObjectException("Passed film id is null");
        }
        Film film = filmStorage.getElement(filmId);
        log.info("Searching for film with id = {}", filmId);
        if (film == null) {
            throw new NotFoundException("Film with id = {} not found " + filmId);
        }

        Set<Integer> directorsIds = directorService.getDirectorsIdsOfFilm(filmId);
        List<Director> directors = directorService.getDirectorByIds(directorsIds);
        film.getDirectors().addAll(new LinkedHashSet<>(directors));

        return FilmMapper.mapToFilmDto(film);
    }

    @Override
    public FilmDto createFilm(NewFilmRequest newFilmRequest) {
        log.info("Validation check when creating a film with name - {} ", newFilmRequest.getName());
        validateRating(newFilmRequest.getMpa());
        validateGenres(newFilmRequest.getGenres());
        Set<Integer> directorsIds = validateDirectors(newFilmRequest);


        Film film = FilmMapper.mapToFilm(newFilmRequest);
        film.validate();
        Film newFilm = filmStorage.addElement(film);
        directorService.insertFilmAndDirector(newFilm.getId(), directorsIds);
        log.info("Added a new record(s) to films_directors with film id = {} and director id(s) = {} ", newFilm.getId(), directorsIds);
        return FilmMapper.mapToFilmDto(newFilm);
    }

    @Override
    public FilmDto updateFilm(UpdateFilmRequest updateFilmRequest) {
        log.info("Validation check when updating a film with name - {} ", updateFilmRequest);
        validateRating(updateFilmRequest.getMpa());
        validateGenres(updateFilmRequest.getGenres());
        Set<Integer> directorsIds = validateDirectors(updateFilmRequest);

        Film film = FilmMapper.mapToFilm(updateFilmRequest);
        film.validate();
        Film oldFilm = filmStorage.getElement(film.getId());
        log.info("Deleting all rows from films_directors with film id = {}", film.getId());
        directorService.deleteFilmsAndDirectors(film.getId());
        log.info("Adding a new record(s) to films_directors with film id = {} and director id(s) = {} ", film.getId(), directorsIds);
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
    public List<FilmDto> getFilmsByDirectorSorted(Integer directorId, String sort) {
        List<Film> films;
        Director director = directorService.getDirector(directorId);
        log.info("Searching for films of director with id = {}, sorted by - {} ", directorId, sort);
        if (sort.equalsIgnoreCase("year")) {
            films = filmStorage.getSortedFilmsByYear(directorId);
        } else if (sort.equalsIgnoreCase("likes")) {
            films = filmStorage.getSortedFilmsByLikes(directorId);
        } else {
            throw new ValidationException("Invalid sort filter entered");
        }

        return films.stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    private void validateRating(Rating rating) {
        log.info("Validation of rating - {}", rating);
        if (rating == null
                || !ratingStorage.isValidRatingId(rating.getId())) {
            throw new ValidationException("Film rating is not specified or not found");
        }
    }

    private void validateGenres(Collection<Genre> genreList) {
        log.info("Genre validation check");
        if (genreList != null) {
            for (Genre genre : genreList) {
                if (!genreStorage.isValidGenreId(genre.getId())) {
                    throw new ValidationException("Genre of the film with id = " + genre.getId() + " not specified or not found");
                }
            }
        }
    }

    private Set<Integer> validateDirectors(NewFilmRequest newFilmRequest) {
        log.info("Director validation check");
        if (newFilmRequest.getDirectors() != null) {
            Set<Integer> directorsIds = newFilmRequest.getDirectors().stream()
                    .map(Director::getId)
                    .collect(Collectors.toSet());
            List<Director> directors = directorService.getDirectorByIds(directorsIds);
            if (directorsIds.size() != directors.size()) {
                throw new ValidationException("Entered invalid director id");
            }
            newFilmRequest.setDirectors(new LinkedHashSet<>(directors));
            return directorsIds;
        }
        return new LinkedHashSet<>();
    }

    @Override
    public List<FilmDto> getCommonFilmsLikesByUsers(Integer userId, Integer friendId) {
        return filmStorage.getFilmsLikesByUsers(userId, friendId).stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    @Override
    public List<FilmDto> getFilmsByContext(String query, String criterion) {
        SearchParams searchParams = new SearchParams(query, criterion);
        return filmStorage.findFilmsBySearchParameters(searchParams).stream()
                .sorted(Comparator.comparing(film -> film.getMpa().getId()))
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

}
