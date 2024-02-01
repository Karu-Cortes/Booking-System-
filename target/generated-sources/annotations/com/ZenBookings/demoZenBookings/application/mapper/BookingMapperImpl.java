package com.ZenBookings.demoZenBookings.application.mapper;

import com.ZenBookings.demoZenBookings.application.lasting.ERole;
import com.ZenBookings.demoZenBookings.application.lasting.EState;
import com.ZenBookings.demoZenBookings.domain.dto.BookingDto;
import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.Booking;
import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-01T12:29:23-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking toEntity(BookingDto dto) {
        if ( dto == null ) {
            return null;
        }

        Booking.BookingBuilder booking = Booking.builder();

        booking.id( dto.id() );
        booking.date( dto.date() );
        booking.phone( dto.phone() );
        booking.state( dto.state() );
        booking.user( userDtoToUser( dto.user() ) );
        booking.spa( spaServiceDtoToSpaService( dto.spa() ) );

        return booking.build();
    }

    @Override
    public BookingDto toDto(Booking entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        LocalDateTime date = null;
        String phone = null;
        EState state = null;
        SpaServiceDto spa = null;
        UserDto user = null;

        id = entity.getId();
        date = entity.getDate();
        phone = entity.getPhone();
        state = entity.getState();
        spa = spaServiceToSpaServiceDto( entity.getSpa() );
        user = userToUserDto( entity.getUser() );

        BookingDto bookingDto = new BookingDto( id, date, phone, state, spa, user );

        return bookingDto;
    }

    @Override
    public List<Booking> toEntityList(List<BookingDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Booking> list = new ArrayList<Booking>( dtoList.size() );
        for ( BookingDto bookingDto : dtoList ) {
            list.add( toEntity( bookingDto ) );
        }

        return list;
    }

    @Override
    public List<BookingDto> toDtoList(List<Booking> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BookingDto> list = new ArrayList<BookingDto>( entityList.size() );
        for ( Booking booking : entityList ) {
            list.add( toDto( booking ) );
        }

        return list;
    }

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.id() );
        user.name( userDto.name() );
        user.email( userDto.email() );
        user.password( userDto.password() );
        user.enable( userDto.enable() );
        user.role( userDto.role() );

        return user.build();
    }

    protected SpaService spaServiceDtoToSpaService(SpaServiceDto spaServiceDto) {
        if ( spaServiceDto == null ) {
            return null;
        }

        SpaService.SpaServiceBuilder spaService = SpaService.builder();

        spaService.id( spaServiceDto.id() );
        spaService.name( spaServiceDto.name() );
        spaService.description( spaServiceDto.description() );

        return spaService.build();
    }

    protected SpaServiceDto spaServiceToSpaServiceDto(SpaService spaService) {
        if ( spaService == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;

        id = spaService.getId();
        name = spaService.getName();
        description = spaService.getDescription();

        SpaServiceDto spaServiceDto = new SpaServiceDto( id, name, description );

        return spaServiceDto;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String email = null;
        String password = null;
        ERole role = null;
        Boolean enable = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
        enable = user.getEnable();

        UserDto userDto = new UserDto( id, name, email, password, role, enable );

        return userDto;
    }
}
