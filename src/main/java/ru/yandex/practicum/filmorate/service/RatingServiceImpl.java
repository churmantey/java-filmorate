package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingStorage ratingStorage;

    @Override
    public List<Rating> getAllMpa() {
        return ratingStorage.getAllElements();
    }

    @Override
    public Rating getMpaById(Integer ratingId) {
        return ratingStorage.getElement(ratingId);
    }
}
