package com.ZenBookings.demoZenBookings.domain.dto;

import com.ZenBookings.demoZenBookings.application.lasting.EState;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDto(
        Integer id,
        LocalDateTime date,
        String phone,
        EState state,
        SpaServiceDto spa,
        UserDto user
) {
}
