package ru.yandex.practicum.filmorate.dto.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

@NoArgsConstructor
public final class ReviewMapper {

    public static ReviewDto mapToReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();

        dto.setReviewId(review.getId());
        dto.setContent(review.getContent());
        dto.setIsPositive(review.getIsPositive());
        dto.setUserId(review.getUserId());
        dto.setFilmId(review.getFilmId());
        dto.setUseful(review.getUseful());

        return dto;
    }
}
