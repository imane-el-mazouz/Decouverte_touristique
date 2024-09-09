package com.tourist.mapper;

import com.tourist.dto.ExcursionDTO;
import com.tourist.model.Excursion;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExcursionMapper {
    Excursion toEntity(ExcursionDTO excursionDTO);

    @AfterMapping
    default void linkReservations(@MappingTarget Excursion excursion) {
        excursion.getReservations().forEach(reservation -> reservation.setExcursion(excursion));
    }

    ExcursionDTO toDto(Excursion excursion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Excursion partialUpdate(ExcursionDTO excursionDTO, @MappingTarget Excursion excursion);
}