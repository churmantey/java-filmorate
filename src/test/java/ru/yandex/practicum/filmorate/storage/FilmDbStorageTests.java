package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTests {

    private final FilmDbStorage filmStorage;
    private Film film;

    @BeforeEach
    public void setUp() {
        this.film = new Film();
        film.setName("film name");
        film.setDescription("In a galaxy far far away");
        film.setReleaseDate(LocalDate.of(1995, 4, 15));
        film.setDuration(100);
        film.setMpa(new Rating(1));
    }

    @Test
    public void testInitialFilmsCount() {
        List<Film> filmList = filmStorage.getAllElements();
        assertThat(filmList).hasSize(4);
        assertThat(filmList.get(0)).hasFieldOrPropertyWithValue("name", "Хищник");
        assertThat(filmList.get(3)).hasFieldOrPropertyWithValue("name", "Король Лев");
    }

    @Test
    public void testFindFilmById() {
        Film foundFilm = filmStorage.getElement(1);
        assertThat(foundFilm)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Хищник");
    }

    @Test
    public void testAddFilm() {
        int initialSize = filmStorage.getAllElements().size();
        filmStorage.addElement(film);
        assertThat(filmStorage.getAllElements().size())
                .isEqualTo(initialSize + 1);
    }

    @Test
    public void testUpdateFilm() {
        Film addedFilm = filmStorage.addElement(film);

        Integer id = addedFilm.getId();
        String name = addedFilm.getName();
        String description = addedFilm.getDescription();

        addedFilm.setName("Updated name");
        addedFilm.setDescription("Updated film description");

        Film updatedFilm = filmStorage.updateElement(addedFilm);

        assertThat(updatedFilm.getId()).isEqualTo(id);
        assertThat(updatedFilm.getName())
                .isEqualTo("Updated name")
                .isNotEqualTo(name);
        assertThat(updatedFilm.getDescription())
                .isEqualTo("Updated film description")
                .isNotEqualTo(description);
    }

    @Test
    public void testDeleteFilm() {
        List<Film> filmList = filmStorage.getAllElements();
        int initialFilmsCount = filmList.size();
        filmStorage.addElement(film);
        List<Film> newFilmList = filmStorage.getAllElements();
        int increasedUsersCount = newFilmList.size();
        assertThat(increasedUsersCount).isEqualTo(initialFilmsCount + 1);
        filmStorage.deleteElementById(film.getId());
        assertThat(filmStorage.getAllElements().size()).isEqualTo(initialFilmsCount);
        assertThat(filmStorage.deleteElement(film)).isFalse();
    }

    @Test
    public void testAddLike() {
        film = filmStorage.addElement(film);
        assertThat(film.getLikes()).hasSize(0);
        filmStorage.addLike(film.getId(), 1);
        film = filmStorage.getElement(film.getId());
        assertThat(film.getLikes()).hasSize(1);
        assertThat(film.getLikes().toArray()[0])
                .hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testRemoveLike() {
        film = filmStorage.getElement(1); // Это фильм изначально с двумя лайками
        assertThat(film.getLikes()).hasSize(2);
        filmStorage.removeLike(film.getId(), 1);
        film = filmStorage.getElement(film.getId());
        assertThat(film.getLikes()).hasSize(1);
    }

    @Test
    public void testTopRated() {
        List<Film> topList = filmStorage.getTopRatedFilms(5);
        assertThat(topList).hasSizeGreaterThan(1);
        for (int i = 0; i < topList.size() - 1; i++) {
            assertThat(topList.get(i).getLikes().size())
                    .isGreaterThanOrEqualTo(topList.get(i + 1).getLikes().size());
        }
    }

}
