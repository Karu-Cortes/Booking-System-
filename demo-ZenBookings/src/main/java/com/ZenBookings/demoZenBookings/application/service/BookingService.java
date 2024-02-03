package com.ZenBookings.demoZenBookings.application.service;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import com.ZenBookings.demoZenBookings.application.mapper.BookingMapper;
import com.ZenBookings.demoZenBookings.domain.dto.BookingDto;
import com.ZenBookings.demoZenBookings.domain.entity.Booking;
import com.ZenBookings.demoZenBookings.domain.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public record BookingService(
    BookingRepository bookingRepository,
    BookingMapper bookingMapper
) {

    /**
     * toma un objeto BookingDto como parámetro. Convierte el BookingDto a una entidad Booking utilizando un mapeador,
     * y luego guarda la entidad Booking usando un repositorio.
     *
     * @param  bookingDto   el BookingDto a registrar
     * @return              void
     */
    public void registerBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);
        bookingRepository.save(booking);
    }

    /**
     * recupera una lista paginada de reservas. Toma un desplazamiento y un límite como parámetros para la paginación,
     * y devuelve una lista de objetos BookingDto. Si no se encuentra ningún dato, lanza una ZenBookingException
     * con el mensaje "DATA_NOT_FOUND".
     *
     * @param  offset   el desplazamiento para la paginación
     * @param  limit    el límite para la paginación
     * @return          una lista de BookingDto
     */
    public List<BookingDto> findAllBooking(Integer offset, Integer limit) throws ZenBookingException {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Booking> spas = bookingRepository.findAll(pageable);
        if (spas.getContent().isEmpty()) {
            throw new ZenBookingException(EMessage.DATA_NOT_FOUND);
        }
        return bookingMapper.toDtoList(spas.getContent());
    }

    /**
     * toma un ID como entrada y devuelve un objeto BookingDto. Busca una reserva con el ID dado en un repositorio
     * y lanza una excepción ZenBookingException si la reserva no se encuentra. Si la reserva se encuentra,
     * la mapea a un objeto BookingDto utilizando un bookingMapper y lo devuelve.
     *
     * @param  id   el ID de la reserva a encontrar
     * @return      el DTO de la reserva si se encuentra
     * @throws ZenBookingException  si la reserva no se encuentra
     */
    public BookingDto findBookingById(Integer id) throws ZenBookingException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        return bookingMapper.toDto(booking);
    }

    /**
     * toma un ID y datos de reserva como entrada. Verifica si existe una reserva con el ID dado, luego actualiza
     * la reserva con los datos proporcionados. Si no se encuentra ninguna reserva con el ID dado, lanza una
     * ZenBookingException con el mensaje "DATA_NOT_FOUND".
     *
     * @param  id          el ID de la reserva que se va a editar
     * @param  bookingDto  los datos para actualizar la reserva
     */
    public void editBooking(Integer id, BookingDto bookingDto) throws ZenBookingException {
        bookingRepository.findById(id).orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        Booking booking = bookingMapper.toEntity(bookingDto);
        bookingRepository.save(booking);
    }

    /**
     * eliminar una reserva por su ID. Primero intenta encontrar la reserva por su ID en el repositorio,
     * y si no existe, lanza una ZenBookingException con el mensaje "Datos no encontrados". Si la reserva
     * es encontrada, entonces es eliminada del repositorio.
     *
     * @param  id   el ID de la reserva a eliminar
     * @throws ZenBookingException  si no se encuentra la reserva
     */
    public void removeBooking(Integer id) throws ZenBookingException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        bookingRepository.delete(booking);
    }
}
