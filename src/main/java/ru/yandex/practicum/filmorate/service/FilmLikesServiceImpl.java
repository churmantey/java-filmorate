package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmLikesServiceImpl implements FilmLikesService {

    private final FilmService filmService;
    private final UserService userService;

    @Override
    public Film addLike(Integer filmId, Integer userId) {
        Film film = filmService.getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.getLikes().add(user.getId());
        return film;
    }

    @Override
    public Film removeLike(Integer filmId, Integer userId) {
        Film film = filmService.getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.getLikes().remove(user.getId());
        return film;
    }

    @Override
    public List<Film> getTopRatedFilms(int count) {
        return filmService.getAllFilms().stream()
                .sorted(new FilmLikesComparator().reversed())
                .limit(count)
                .toList();
    }

}
