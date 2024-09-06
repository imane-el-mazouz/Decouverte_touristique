package com.tourist.mapper;

import com.tourist.dto.HotelDTO;
import com.tourist.model.Hotel;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {
    Hotel toEntity(HotelDTO hotelDTO);

    @AfterMapping
    default void linkRooms(@MappingTarget Hotel hotel) {
        hotel.getRooms().forEach(room -> room.setHotel(hotel));
    }

    HotelDTO toDto(Hotel hotel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Hotel partialUpdate(HotelDTO hotelDTO, @MappingTarget Hotel hotel);
}