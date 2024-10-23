package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.director.DirectorStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorStorage directorStorage;

    @Override
    public List<Director> getAll() {
        return directorStorage.getAllElements();
    }

    @Override
    public Director createDirector(Director director) {
        return directorStorage.addElement(director);
    }

    @Override
    public Director getDirector(Integer id) {
        return directorStorage.getElement(id);
    }

    @Override
    public Director updateDirector(Director director) {
        Director savedDirector = getDirector(director.getId());
        directorStorage.updateElement(director);
        return director;
    }

    @Override
    public boolean deleteDirector(Integer id) {
        return directorStorage.deleteElementById(id);
    }

    @Override
    public List<Director> getDirectorByIds(Set<Integer> directorIds) {
        return directorStorage.getDirectorByIds(directorIds);
    }

    @Override
    public Set<Integer> getDirectorsIdsOfFilm(Integer id) {
        return directorStorage.getDirectorIdsOfFilm(id);
    }

    @Override
    public void insertFilmAndDirector(Integer film_id, Set<Integer> directorIds) {
        directorStorage.insertFilmAndDirectors(film_id, directorIds);
    }

    @Override
    public List<Director> getAllDirectorForOneFilm(Integer id) {
        return directorStorage.getDirectorsByFilmId(id);
    }

}
