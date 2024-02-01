package com.ZenBookings.demoZenBookings.application.mapper;

import com.ZenBookings.demoZenBookings.application.lasting.ERole;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-01T12:29:20-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.id() );
        user.name( dto.name() );
        user.email( dto.email() );
        user.password( dto.password() );
        user.enable( dto.enable() );
        user.role( dto.role() );

        return user.build();
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String email = null;
        String password = null;
        ERole role = null;
        Boolean enable = null;

        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        role = entity.getRole();
        enable = entity.getEnable();

        UserDto userDto = new UserDto( id, name, email, password, role, enable );

        return userDto;
    }

    @Override
    public List<User> toEntityList(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> toDtoList(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
