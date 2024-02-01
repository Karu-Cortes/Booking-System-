package com.ZenBookings.demoZenBookings.application.mapper;

import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-01T12:29:22-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class SpaServiceMapperImpl implements SpaServiceMapper {

    @Override
    public SpaService toEntity(SpaServiceDto dto) {
        if ( dto == null ) {
            return null;
        }

        SpaService.SpaServiceBuilder spaService = SpaService.builder();

        spaService.id( dto.id() );
        spaService.name( dto.name() );
        spaService.description( dto.description() );

        return spaService.build();
    }

    @Override
    public SpaServiceDto toDto(SpaService entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();

        SpaServiceDto spaServiceDto = new SpaServiceDto( id, name, description );

        return spaServiceDto;
    }

    @Override
    public List<SpaService> toEntityList(List<SpaServiceDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SpaService> list = new ArrayList<SpaService>( dtoList.size() );
        for ( SpaServiceDto spaServiceDto : dtoList ) {
            list.add( toEntity( spaServiceDto ) );
        }

        return list;
    }

    @Override
    public List<SpaServiceDto> toDtoList(List<SpaService> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SpaServiceDto> list = new ArrayList<SpaServiceDto>( entityList.size() );
        for ( SpaService spaService : entityList ) {
            list.add( toDto( spaService ) );
        }

        return list;
    }
}
