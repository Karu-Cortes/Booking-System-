package com.ZenBookings.demoZenBookings.domain.dto;
import com.ZenBookings.demoZenBookings.application.lasting.ERole;
import com.ZenBookings.demoZenBookings.domain.entity.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Integer id,
        String name,
        String email,
        @JsonIgnore(value = false)
        String password,
        ERole role,
        List<Booking> bookings,
        Boolean enable) {
}
