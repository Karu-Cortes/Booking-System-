package com.ZenBookings.demoZenBookings.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpaServiceDto(
         Integer id,
        String name,
        String description,
        String imageUrl) {
}
