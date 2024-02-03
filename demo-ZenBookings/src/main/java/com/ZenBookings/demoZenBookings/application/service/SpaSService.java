package com.ZenBookings.demoZenBookings.application.service;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import com.ZenBookings.demoZenBookings.application.mapper.SpaServiceMapper;
import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import com.ZenBookings.demoZenBookings.domain.repository.SpaServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.List;


@Service
public record SpaSService(
        SpaServiceRepository spaRepository,
        SpaServiceMapper spaMapper
) {
    /**
     * oma un objeto SpaServiceDto como entrada, lo convierte a un objeto
     * SpaService usando un spaMapper, y luego lo guarda usando un spaRepository
     *
     * @param  spaDto   el DTO que representa el servicio de spa a registrar
     * @return          void
     */
    public void registerSpa(SpaServiceDto spaDto) {
        SpaService spa = spaMapper.toEntity(spaDto);
        spaRepository.save(spa);
    }





    /**
     * recupera servicios de spa con paginación. Toma offset y limit como parámetros para especificar el
     * rango de resultados a devolver. Utiliza un objeto Pageable para crear la paginación, consulta los
     * servicios de spa utilizando un spaRepository, y luego mapea los resultados a objetos SpaServiceDto
     * antes de devolverlos. Si no se encuentran resultados, lanza una ZenBookingException con un mensaje que
     * indica que no se encontraron datos.
     *
     * @param  offset   el desplazamiento del primer resultado a devolver
     * @param  limit    el número máximo de resultados a devolver
     * @return          una lista de objetos SpaServiceDto
     */
    public List<SpaServiceDto> findAllSpa(Integer offset, Integer limit) throws ZenBookingException {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<SpaService> spas = spaRepository.findAll(pageable);
        if (spas.getContent().isEmpty()) {
            throw new ZenBookingException(EMessage.DATA_NOT_FOUND);
        }
        return spaMapper.toDtoList(spas.getContent());
    }

    /**
     * encuentra un servicio de spa por su ID. Utiliza un repositorio para buscar el servicio
     * de spa, y si no se encuentra, lanza una excepción personalizada. Luego mapea el servicio
     * de spa a un objeto de transferencia de datos (DTO) y lo devuelve.
     *
     * @param  id   el ID del servicio de SPA
     * @return      el DTO que representa el servicio de SPA
     */

    public SpaServiceDto findSpaById(Integer id) throws ZenBookingException {
        SpaService spa = spaRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        return spaMapper.toDto(spa);
    }



    public void editSpa(Integer id, SpaServiceDto spaDto) throws ZenBookingException {
        SpaService existingSpa = spaRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));

        if (spaDto.name() != null) {
            existingSpa.setName(spaDto.name());
        }
        if (spaDto.description() != null) {
            existingSpa.setDescription(spaDto.description());
        }
        spaRepository.save(existingSpa);
    }



    /**
     * toma un id como entrada y trata de encontrar un SpaService con ese id en el spaRepository.
     * Si se encuentra el SpaService, se elimina del repositorio. Si no se encuentra, se lanza una
     * ZenBookingException con un mensaje de "DATA_NOT_FOUND".
     *
     * @param  id   el ID del spa a eliminar
     * @throws ZenBookingException  si no se encuentra el spa con el ID proporcionado
     */

    public void removeSpa(Integer id) throws ZenBookingException {
        SpaService spa = spaRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        spaRepository.delete(spa);
    }
}
