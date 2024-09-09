package com.tourist.mapper;

import com.tourist.dto.RoomDTO;
import com.tourist.model.Room;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {
    Room toEntity(RoomDTO roomDTO);

    @AfterMapping
    default void linkReservations(@MappingTarget Room room) {
        room.getReservations().forEach(reservation -> reservation.setRoom(room));
    }

    RoomDTO toDto(Room room);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Room partialUpdate(RoomDTO roomDTO, @MappingTarget Room room);
}