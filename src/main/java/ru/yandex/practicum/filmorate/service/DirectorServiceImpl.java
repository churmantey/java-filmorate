package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.dto.mapper.DirectorMapper;
import ru.yandex.practicum.filmorate.storage.director.DirectorStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorStorage directorStorage;

    @Override
    public List<DirectorDto> getAllDirectors() {
        return directorStorage.getAllElements().stream()
                .map(DirectorMapper::mapToDirectorDto)
                .toList();
    }

    @Override
    public DirectorDto createDirector(DirectorDto directorDto) {
        return DirectorMapper.mapToDirectorDto(
                directorStorage.addElement(DirectorMapper.mapToDirector(directorDto))
        );
    }

    @Override
    public DirectorDto getDirector(Integer id) {
        return DirectorMapper.mapToDirectorDto(directorStorage.getElement(id));
    }

    @Override
    public DirectorDto updateDirector(DirectorDto directorDto) {
        DirectorDto savedDirector = getDirector(directorDto.getId());
        directorStorage.updateElement(DirectorMapper.mapToDirector(directorDto));
        return directorDto;
    }

    @Override
    public boolean deleteDirector(Integer id) {
        return directorStorage.deleteElementById(id);
    }

    @Override
    public List<DirectorDto> getDirectorByIds(Set<Integer> directorIds) {
        return directorStorage.getDirectorByIds(directorIds).stream()
                .map(DirectorMapper::mapToDirectorDto)
                .toList();
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
        return directorStorage.getDirectorsByFilmId(id).stream()
                .map(DirectorMapper::mapToDirectorDto)
                .toList();
    }

}
