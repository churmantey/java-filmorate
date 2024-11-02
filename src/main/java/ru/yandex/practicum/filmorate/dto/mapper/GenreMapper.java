package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.IdEntity;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre mapIdEntityToGenre(IdEntity idEntity);

    Set<Genre> mapIdEntitySetToGenreSet(Set<IdEntity> idEntities);

    IdEntity mapGenreToIdEntity(Genre genre);

    Set<IdEntity> mapGenresSetToIdEntitySet(Set<Genre> genres);

}
