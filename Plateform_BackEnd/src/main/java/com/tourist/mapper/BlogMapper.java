package com.tourist.mapper;

import com.tourist.dto.BlogDTO;
import com.tourist.model.Blog;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlogMapper {
    Blog toEntity(BlogDTO blogDTO);

    BlogDTO toDto(Blog blog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Blog partialUpdate(BlogDTO blogDTO, @MappingTarget Blog blog);
}