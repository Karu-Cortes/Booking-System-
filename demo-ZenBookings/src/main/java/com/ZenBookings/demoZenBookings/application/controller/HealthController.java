package com.ZenBookings.demoZenBookings.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health")
public record HealthController() {

    /**
     * devuelve una entidad de respuesta HTTP. La entidad de respuesta contiene un mensaje que
     * indica el estado de salud de la API y el código de estado HTTP OK (200).
     * El método está mapeado al método HTTP GET.
     *
     * @return         	Entidad de respuesta con el mensaje de estado de salud y el estado HTTP OK
     */
    @GetMapping
    public ResponseEntity<?> getHealth(){
        return new ResponseEntity<>("Toda la API funciona bien", HttpStatus.OK);
    }
}
