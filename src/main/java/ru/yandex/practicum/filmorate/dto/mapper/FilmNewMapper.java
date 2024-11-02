package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

@Mapper(componentModel = "spring", uses = {DirectorMapper.class, GenreMapper.class})
public interface FilmNewMapper {

    @Mapping(target = "id", ignore = true)
    Film mapRequestToFilm(NewFilmRequest newFilmRequest);

    Film mapRequestToFilm(UpdateFilmRequest updateFilmRequest);

    FilmDto mapFilmToDto(Film film);

    default IdEntity mapRatingToIdEntity(Rating rating) {
        return new IdEntity(rating.getId(), rating.getName());
    }

}
