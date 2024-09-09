package com.tourist.mapper;

import com.tourist.dto.ReservationDTO;
import com.tourist.model.Reservation;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {
    Reservation toEntity(ReservationDTO reservationDTO);

    ReservationDTO toDto(Reservation reservation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reservation partialUpdate(ReservationDTO reservationDTO, @MappingTarget Reservation reservation);
}