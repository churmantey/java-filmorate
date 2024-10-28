package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.UserRecommendationsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRecommendationsController {

    private final UserRecommendationsService userRecommendationsService;

    //GET /users/{id}/recommendations
    @GetMapping("/{userId}/recommendations")
    public List<FilmDto> getUserRecommendations(@PathVariable Integer userId) {
        log.info("GET user {} recommendations", userId);
        List<FilmDto> filmList = userRecommendationsService.getRecommendedFilms(userId);
        log.info("GET RESPONSE user {} recommendations {}", userId, filmList);
        return filmList;
    }


}
