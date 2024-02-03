package com.ZenBookings.demoZenBookings.application.controller;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.service.SpaSService;
import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/spa")
public record SpaSController(
    SpaSService spaSService
) {

    /**
     * registrar una nueva Aplicación de Página Única (SPA) utilizando el objeto de transferencia de
     * datos del servicio SPA proporcionado. Es un método de solicitud POST con un requisito de seguridad
     * para autenticación de portador, y devuelve un ResponseEntity con el código de estado HTTP 201 (CREADO).
     *
     * @param  spaDto  el objeto de transferencia de datos del servicio SPA a registrar
     * @return         un ResponseEntity con el estado HTTP de CREADO
     */
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> registerSpa(@RequestBody SpaServiceDto spaDto) {
        spaSService.registerSpa(spaDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * encontrar servicios de spa dentro de un offset y límite especificados para paginación.
     * Utiliza la anotación @GetMapping de Spring para mapear el método a una URL de endpoint específica.
     * El método recupera servicios de spa usando el offset y límite proporcionados, y devuelve una lista de objetos
     * SpaServiceDto junto con un código de estado HTTP en un objeto ResponseEntity. La anotación @SecurityRequirement
     * indica que el método requiere autenticación de portador.
     *
     * @param  offset  el offset para la paginación
     * @param  limit   el límite para la paginación
     * @return         un ResponseEntity que contiene la lista de SpaServiceDto y el estado HTTP
     */
    @GetMapping("/{offset}/{limit}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findAllSpa(
            @PathVariable("offset") Integer offset,
            @PathVariable("limit") Integer limit) throws ZenBookingException {
        List<SpaServiceDto> spas = spaSService.findAllSpa(offset, limit);
        return new ResponseEntity<>(spas, HttpStatus.FOUND);
    }

    /**
     * recupera un spa por su ID utilizando una solicitud GET. Incluye un requisito de seguridad para la autenticación
     * de portador y devuelve el spa junto con un código de estado HTTP.
     *
     * @param  id   el ID del spa a encontrar
     * @return      un ResponseEntity que contiene el spa y el estado HTTP
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findSpaById(@PathVariable("id") Integer id) throws ZenBookingException {
        SpaServiceDto spa = spaSService.findSpaById(id);
        return new ResponseEntity<>(spa, HttpStatus.FOUND);
    }

    /**
     *  edita un servicio de spa. Utiliza la anotación @PutMapping para mapear las solicitudes
     *  HTTP PUT al punto final especificado ("/{id}"). El método recibe el ID del servicio de spa a
     *  editar y un DTO que contiene la información actualizada. Luego llama al método editSpa del spaSService
     *  para actualizar el servicio de spa. Finalmente, devuelve un ResponseEntity con el código de estado NO_CONTENT.
     *  El método también está anotado con @SecurityRequirement para especificar que requiere autenticación de portador.
     *
     * @param  id     el ID del servicio de spa a editar
     * @param  spaDto el DTO que contiene la información actualizada del servicio de spa
     * @return        un ResponseEntity con estado NO_CONTENT
     */
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> editSpa(@PathVariable("id") Integer id, @RequestBody SpaServiceDto spaDto) throws
            ZenBookingException {
        spaSService.editSpa(id, spaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * elimina un spa por su ID. Es un punto final de tipo DELETE con una variable de ruta para el ID del spa.
     * El método utiliza la anotación @SecurityRequirement para especificar que se requiere autenticación del
     * portador. Devuelve un ResponseEntity con un estado de NO_CONTENT después de eliminar el spa.
     *
     * @param  id   el ID del spa que se va a eliminar
     * @return      un ResponseEntity con el código de estado NO_CONTENT
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> removeSpa(@PathVariable("id") Integer id) throws ZenBookingException {
        spaSService.removeSpa(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
