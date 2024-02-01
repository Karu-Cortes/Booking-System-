package com.ZenBookings.demoZenBookings.application.mapper.base;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public interface IBaseMapper {
    IBaseMapper INSTANCE = Mappers.getMapper(IBaseMapper.class);
}
