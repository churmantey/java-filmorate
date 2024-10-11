package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

public interface RatingStorage extends BaseStorage<Rating> {

    public  Rating getFilmRatingById(Integer ratingId);

    public boolean isValidRatingId(Integer id);

}
