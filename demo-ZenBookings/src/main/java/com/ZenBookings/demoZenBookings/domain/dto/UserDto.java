package com.ZenBookings.demoZenBookings.domain.dto;
import com.ZenBookings.demoZenBookings.application.lasting.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Integer id,
        String name,
        String email,
        @JsonIgnore(value = false)
        String password,
        ERole role,
        Boolean enable) {
}
