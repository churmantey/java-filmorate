package ru.yandex.practicum.filmorate.storage.review;


public interface ReviewRatingStorage {

    boolean isUserRated(Integer id, Integer userId);

    void addRating(Integer id, Integer userId, String type);

    void updateRating(Integer id, Integer userId, String type);

    void deleteRating(Integer id, Integer userId);

    boolean isUserLiked(Integer id, Integer userId);

    boolean isUserDisliked(Integer id, Integer userId);
}
