package com.ZenBookings.demoZenBookings.application.mapper;

import com.ZenBookings.demoZenBookings.application.mapper.base.IBaseMapper;

import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SpaServiceMapper extends IBaseMapper {

    SpaService toEntity(SpaServiceDto dto);

    SpaServiceDto toDto(SpaService entity);

    List<SpaService> toEntityList(List<SpaServiceDto> dtoList);

    List<SpaServiceDto> toDtoList(List<SpaService> entityList);
}
