package com.ZenBookings.demoZenBookings.application.mapper;

import com.ZenBookings.demoZenBookings.application.mapper.base.IBaseMapper;

import com.ZenBookings.demoZenBookings.domain.dto.BookingDto;
import com.ZenBookings.demoZenBookings.domain.entity.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper extends IBaseMapper {

    Booking toEntity(BookingDto dto);

    BookingDto toDto(Booking entity);

    List<Booking> toEntityList(List<BookingDto> dtoList);

    List<BookingDto> toDtoList(List<Booking> entityList);
}
