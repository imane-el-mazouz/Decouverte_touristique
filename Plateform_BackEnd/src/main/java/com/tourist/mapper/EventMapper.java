package com.tourist.mapper;

import com.tourist.dto.EventDTO;
import com.tourist.model.Event;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    Event toEntity(EventDTO eventDTO);

    @AfterMapping
    default void linkReservations(@MappingTarget Event event) {
        event.getReservations().forEach(reservation -> reservation.setEvent(event));
    }

    EventDTO toDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event partialUpdate(EventDTO eventDTO, @MappingTarget Event event);
}