package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    Director dtoToDirector(DirectorDto directorDto);

    List<Director> dtoListToDirector(List<DirectorDto> directorDtos);

    List<Director> dtoSetToDirector(Set<DirectorDto> directorDtos);

    DirectorDto directorToDto(Director director);

    List<DirectorDto> directorListToDto(List<Director> directors);

    List<DirectorDto> directorSetToDto(Set<Director> directors);
}
