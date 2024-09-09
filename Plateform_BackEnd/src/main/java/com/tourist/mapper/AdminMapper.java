package com.tourist.mapper;

import com.tourist.dto.AdminDTO;
import com.tourist.model.Admin;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    Admin toEntity(AdminDTO adminDTO);

    AdminDTO toDto(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(AdminDTO adminDTO, @MappingTarget Admin admin);
}