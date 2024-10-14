package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingDbStorage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan("ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RatingDbStorageTests {

    private final RatingDbStorage ratingStorage;

    @Test
    public void testGetRating() {
        Rating rating = ratingStorage.getElement(1);
        assertThat(rating.getName()).isEqualTo("G");
        assertThat(rating.getDescription()).isEqualTo("у фильма нет возрастных ограничений");
        rating = ratingStorage.getElement(5);
        assertThat(rating.getName()).isEqualTo("NC-17");
        assertThat(rating.getDescription()).isEqualTo("лицам до 18 лет просмотр запрещён.");
    }

    @Test
    public void testGetAllRatings() {
        List<Rating> ratingList = ratingStorage.getAllElements();
        assertThat(ratingList).hasSize(5);
    }

    @Test
    public void testIsValidRatingId() {
        assertThat(ratingStorage.isValidRatingId(0)).isFalse();
        assertThat(ratingStorage.isValidRatingId(1)).isTrue();
        assertThat(ratingStorage.isValidRatingId(2)).isTrue();
        assertThat(ratingStorage.isValidRatingId(3)).isTrue();
        assertThat(ratingStorage.isValidRatingId(4)).isTrue();
        assertThat(ratingStorage.isValidRatingId(5)).isTrue();
        assertThat(ratingStorage.isValidRatingId(6)).isFalse();
        assertThat(ratingStorage.isValidRatingId(9999)).isFalse();
    }

}
