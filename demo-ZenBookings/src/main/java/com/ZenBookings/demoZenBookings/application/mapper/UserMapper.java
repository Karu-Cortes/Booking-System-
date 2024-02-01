package com.ZenBookings.demoZenBookings.application.mapper;
import com.ZenBookings.demoZenBookings.application.mapper.base.IBaseMapper;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends IBaseMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    List<User> toEntityList(List<UserDto> dtoList);

    List<UserDto> toDtoList(List<User> entityList);

}