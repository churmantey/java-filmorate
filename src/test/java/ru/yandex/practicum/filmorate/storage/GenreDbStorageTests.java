package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTests {

    private final GenreDbStorage genreStorage;

    @Test
    public void testGetGenre() {
        assertThat(genreStorage.getElement(1).getName()).isEqualTo("Комедия");
        assertThat(genreStorage.getElement(6).getName()).isEqualTo("Боевик");
        assertThrows(NotFoundException.class, () -> genreStorage.getElement(7));
    }

    @Test
    public void testGetAllGenres() {
        List<Genre> genreList = genreStorage.getAllElements();
        assertThat(genreList).hasSize(6);
    }

    @Test
    public void testGetFilmGenresById() {
        LinkedHashSet<Genre> filmGenres = (LinkedHashSet<Genre>) genreStorage.getFilmGenresById(1);
        assertThat(filmGenres).hasSize(2);
        assertThat(filmGenres.getFirst().getId()).isLessThan(filmGenres.getLast().getId());
    }

    @Test
    public void testIsValidGenreId() {
        assertThat(genreStorage.isValidGenreId(0)).isFalse();
        assertThat(genreStorage.isValidGenreId(1)).isTrue();
        assertThat(genreStorage.isValidGenreId(6)).isTrue();
        assertThat(genreStorage.isValidGenreId(7)).isFalse();
        assertThat(genreStorage.isValidGenreId(99999)).isFalse();
    }

    @Test
    public void testDeleteFilmGenresById() {
        LinkedHashSet<Genre> filmGenres = (LinkedHashSet<Genre>) genreStorage.getFilmGenresById(1);
        assertThat(filmGenres).hasSize(2);
        genreStorage.deleteFilmGenresById(1);
        assertThat(genreStorage.getFilmGenresById(1)).hasSize(0);
    }

    @Test
    public void testUpdateFilmGenresById() {
        LinkedHashSet<Genre> genreList = new LinkedHashSet<>(
                Set.of(new Genre(1, "Комедия"),
                        new Genre(3, "Мультфильм"),
                        new Genre(6, "Боевик"))
        );
        assertThat(genreStorage.getFilmGenresById(3)).hasSize(0);
        genreStorage.updateFilmGenresById(3, genreList);
        assertThat(genreStorage.getFilmGenresById(3)).hasSize(3);
    }

}
