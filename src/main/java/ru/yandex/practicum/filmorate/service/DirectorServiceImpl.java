package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.dto.mapper.DirectorMapperStruct;
import ru.yandex.practicum.filmorate.storage.director.DirectorStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorStorage directorStorage;
    private final DirectorMapperStruct directorMapper;

    @Override
    public List<DirectorDto> getAllDirectors() {
        return directorMapper.directorListToDto(directorStorage.getAllElements());
    }

    @Override
    public DirectorDto createDirector(DirectorDto directorDto) {
        return directorMapper.directorToDto(
                directorStorage.addElement(directorMapper.dtoToDirector(directorDto))
        );
    }

    @Override
    public DirectorDto getDirector(Integer id) {
        return directorMapper.directorToDto(directorStorage.getElement(id));
    }

    @Override
    public DirectorDto updateDirector(DirectorDto directorDto) {
        DirectorDto savedDirector = getDirector(directorDto.getId());
        directorStorage.updateElement(directorMapper.dtoToDirector(directorDto));
        return directorDto;
    }

    @Override
    public boolean deleteDirector(Integer id) {
        return directorStorage.deleteElementById(id);
    }

    @Override
    public List<DirectorDto> getDirectorByIds(Set<Integer> directorIds) {
        return directorMapper.directorListToDto(
                directorStorage.getDirectorByIds(directorIds)
        );
    }

    @Override
    public Set<Integer> getDirectorsIdsOfFilm(Integer id) {
        return directorStorage.getDirectorIdsOfFilm(id);
    }

    @Override
    public void insertFilmAndDirector(Integer filmId, Set<Integer> directorIds) {
        directorStorage.insertFilmAndDirectors(filmId, directorIds);
    }

    @Override
    public void deleteFilmsAndDirectors(Integer filmId) {
        directorStorage.deleteFilmsAndDirectors(filmId);
    }

    @Override
    public List<DirectorDto> getAllDirectorForOneFilm(Integer id) {
        return directorMapper.directorListToDto(
                directorStorage.getDirectorsByFilmId(id)
        );
    }

}
