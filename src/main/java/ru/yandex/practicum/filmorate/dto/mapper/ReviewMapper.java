package ru.yandex.practicum.filmorate.dto.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

@NoArgsConstructor
public final class ReviewMapper {

    public static ReviewDto mapToReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();

        dto.setReviewId(review.getReviewId());
        dto.setContent(review.getContent());
        dto.setIsPositive(review.getIsPositive());
        dto.setUserId(review.getUserId());
        dto.setFilmId(review.getFilmId());

        if (review.getUseful() == null) {
            review.setUseful(0);
        }
        dto.setUseful(review.getUseful());

        return dto;
    }
}
