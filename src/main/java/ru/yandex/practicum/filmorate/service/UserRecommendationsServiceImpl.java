package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.mapper.FilmNewMapper;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecommendationsServiceImpl implements UserRecommendationsService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final FilmNewMapper filmMapper;

    @Override
    public List<FilmDto> getRecommendedFilms(Integer userId) {
        UserDto userDto = userService.getUserById(userId);
        return filmStorage.getRecommendedFilms(userDto.getId()).stream()
                .map(film -> filmMapper.mapFilmToDto(film))
                .toList();
    }
}
