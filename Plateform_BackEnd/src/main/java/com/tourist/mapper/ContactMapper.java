package com.tourist.mapper;

import com.tourist.dto.ContactDTO;
import com.tourist.model.Contact;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {
    Contact toEntity(ContactDTO contactDTO);

    @AfterMapping
    default void linkRooms(@MappingTarget Contact contact) {
    }

    ContactDTO toDto(Contact contact);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Contact partialUpdate(ContactDTO contactDTO, @MappingTarget Contact contact);
}
