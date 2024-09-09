package com.tourist.mapper;

import com.tourist.dto.TraditionDTO;
import com.tourist.model.Tradition;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraditionMapper {
    Tradition toEntity(TraditionDTO traditionDTO);

    @AfterMapping
    default void linkBlogs(@MappingTarget Tradition tradition) {
        tradition.getBlogs().forEach(blog -> blog.setTradition(tradition));
    }

    TraditionDTO toDto(Tradition tradition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tradition partialUpdate(TraditionDTO traditionDTO, @MappingTarget Tradition tradition);
}