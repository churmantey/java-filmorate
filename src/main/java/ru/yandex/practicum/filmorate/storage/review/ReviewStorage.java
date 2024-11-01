package ru.yandex.practicum.filmorate.storage.review;

import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.List;

public interface ReviewStorage extends BaseStorage<Review> {

    List<Review> getByFilm(int film, int count);

    List<Review> getByFilm(int count);
}
