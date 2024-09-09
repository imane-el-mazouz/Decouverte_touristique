package com.tourist.mapper;

import com.tourist.dto.ClientDTO;
import com.tourist.model.Client;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    Client toEntity(ClientDTO clientDTO);

    @AfterMapping
    default void linkReservations(@MappingTarget Client client) {
        client.getReservations().forEach(reservation -> reservation.setClient(client));
    }

    ClientDTO toDto(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Client partialUpdate(ClientDTO clientDTO, @MappingTarget Client client);
}