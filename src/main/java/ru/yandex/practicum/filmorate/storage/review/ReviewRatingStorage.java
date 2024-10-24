package ru.yandex.practicum.filmorate.storage.review;

public interface ReviewRatingStorage {
    boolean addRating(Integer id, Integer userId);

    boolean deleteRating(Integer id, Integer userId);

    boolean isUserExist(Integer id);
}
