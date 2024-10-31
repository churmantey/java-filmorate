package ru.yandex.practicum.filmorate.dto.mapper;

import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.model.Director;

public final class DirectorMapper {

    public static Director mapToDirector(DirectorDto directorDto) {
        return new Director(directorDto.getId(), directorDto.getName());
    }

    public static DirectorDto mapToDirectorDto(Director director) {
        return new DirectorDto(director.getId(), director.getName());
    }

}
