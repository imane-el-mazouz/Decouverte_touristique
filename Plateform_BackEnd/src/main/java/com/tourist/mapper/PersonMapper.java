//package com.tourist.mapper;
//
//import com.tourist.dto.PersonDTO;
//import com.tourist.model.Person;
//import org.mapstruct.*;
//
//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
//public interface PersonMapper {
//    Person toEntity(PersonDTO personDTO);
//
//    PersonDTO toDto(Person person);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Person partialUpdate(PersonDTO personDTO, @MappingTarget Person person);
//}