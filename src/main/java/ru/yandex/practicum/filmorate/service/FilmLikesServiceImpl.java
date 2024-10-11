package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.IdEntity;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmLikesServiceImpl implements FilmLikesService {

    private final FilmService filmService;
    private final UserService userService;

    @Override
    public FilmDto addLike(Integer filmId, Integer userId) {
        FilmDto film = filmService.getFilmById(filmId);
        UserDto user = userService.getUserById(userId);
        film.getLikes().add(new IdEntity(user.getId()));
        return film;
    }

    @Override
    public FilmDto removeLike(Integer filmId, Integer userId) {
        FilmDto film = filmService.getFilmById(filmId);
        UserDto user = userService.getUserById(userId);
        //film.getLikes().remove(user.getId());
        return film;
    }

    @Override
    public List<FilmDto> getTopRatedFilms(int count) {
        return filmService.getTopRatedFilms(count);
    }

}
