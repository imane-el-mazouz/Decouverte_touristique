package com.tourist.mapper;

import com.tourist.dto.ReviewDTO;
import com.tourist.model.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    Review toEntity(ReviewDTO reviewDTO);

    ReviewDTO toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review partialUpdate(ReviewDTO reviewDTO, @MappingTarget Review review);
}