package ru.yandex.practicum.filmorate.dto.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);

    List<ReviewDto> toDtoList(List<Review> reviews);
}
