package com.ZenBookings.demoZenBookings.application.controller;
import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.service.BookingService;
import com.ZenBookings.demoZenBookings.domain.dto.BookingDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/booking")
public record BookingController(
        BookingService bookingService
) {

    /**
     * manejar una solicitud POST de HTTP. Está asegurado con autenticación de token de portador y registra
     * una reserva utilizando los datos proporcionados en BookingDto. Luego devuelve una respuesta con un
     * estado 201 (Creado).
     *
     * @param  bookingDto  los datos de reserva a registrar
     * @return             una entidad de respuesta con el código de estado HTTP 201 (CREADO)
     */

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> registerBooking(@RequestBody BookingDto bookingDto) {
        bookingService.registerBooking(bookingDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * manejar las solicitudes HTTP GET y encontrar reservas. Toma un desplazamiento y límite como variables
     * de ruta, recupera una lista de reservas usando un servicio y devuelve la lista en un ResponseEntity con
     * un estado HTTP de 302 (FOUND). El método también está anotado con @SecurityRequirement para especificar
     * los requisitos de seguridad para acceder a este punto final.
     *
     * @param  offset  el desplazamiento para obtener reservas
     * @param  limit   el límite para obtener reservas
     * @return         el ResponseEntity que contiene la lista de reservas y el estado HTTP
     */
    @GetMapping("/{offset}/{limit}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findAllBooking(
            @PathVariable("offset") Integer offset,
            @PathVariable("limit") Integer limit) throws ZenBookingException {
        List<BookingDto> bookings = bookingService.findAllBooking(offset, limit);
        return new ResponseEntity<>(bookings, HttpStatus.FOUND);
    }

    /**
     * encontrar una reserva por su ID. Requiere un token de portador para la autenticación y
     * devuelve la información de la reserva junto con el código de estado HTTP 302 Encontrado.
     *
     * @param  id   el ID de la reserva a encontrar
     * @return      el ResponseEntity que contiene la reserva encontrada y el estado HTTP
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findBookingById(@PathVariable("id") Integer id) throws ZenBookingException {
        BookingDto booking = bookingService.findBookingById(id);
        return new ResponseEntity<>(booking, HttpStatus.FOUND);
    }

    /**
     * manejar las solicitudes HTTP de tipo PUT. Toma el ID de la reserva a editar y los nuevos detalles
     * para la reserva. También incluye un requisito de seguridad para la autenticación del portador.
     * Después de editar la reserva utilizando el bookingService, devuelve una entidad de respuesta con un
     * estado de "sin contenido".
     *
     * @param  id          el ID de la reserva a editar
     * @param  bookingDto  los nuevos detalles para la reserva
     * @return             una entidad de respuesta con estado sin contenido
     */
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> editBooking(@PathVariable("id") Integer id, @RequestBody BookingDto bookingDto) throws ZenBookingException {
        bookingService.editBooking(id, bookingDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * eliminar una reserva por su ID. Utiliza la anotación @DeleteMapping para especificar el punto final
     * y @SecurityRequirement para indicar que se requiere autenticación de portador. Luego llama a un servicio
     * para eliminar la reserva y devuelve una respuesta con un estado de NO_CONTENT.
     *
     * @param  id   el ID de la reserva a eliminar
     * @return      una entidad de respuesta con estado sin contenido
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> removeBooking(@PathVariable("id") Integer id) throws ZenBookingException {
        bookingService.removeBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
