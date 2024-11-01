package ru.yandex.practicum.filmorate.storage.review;


public interface ReviewRatingStorage {

    void addRating(Integer id, Integer userId, String type);

    void deleteRating(Integer id, Integer userId);

    boolean isUserLiked(Integer id, Integer userId);

    boolean isUserDisliked(Integer id, Integer userId);
}
