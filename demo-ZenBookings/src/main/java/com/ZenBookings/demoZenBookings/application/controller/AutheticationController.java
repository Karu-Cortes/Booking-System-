package com.ZenBookings.demoZenBookings.application.controller;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.service.AuthenticationService;
import com.ZenBookings.demoZenBookings.domain.dto.AuthenticationDto;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j //generar automáticamente un registro (logger) estático en la clase en la que se coloca
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
public record AutheticationController(
        AuthenticationService authenticationService
) {

    /**
     *  registrar un usuario en una API. Toma un objeto UserDto como entrada, registra al usuario
     *  usando un servicio de autenticación y devuelve un ResponseEntity que contiene un token y un
     *  código de estado HTTP.
     *
     * @param  userDto  el objeto de transferencia de datos del usuario para registrar
     * @return          el ResponseEntity que contiene el token y el código de estado HTTP
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws ZenBookingException {
        String token = authenticationService.register(userDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    /**
     * Utiliza la anotación @PostMapping para manejar las solicitudes HTTP POST al punto final "/authenticate".
     * El método toma un objeto AuthenticationDto como entrada, autentica al usuario utilizando el
     * authenticationService y devuelve una entidad de respuesta HTTP con el token de autenticación.
     *
     * @param  authenticationDto  el objeto de transferencia de datos para la autenticación
     * @return                    una entidad de respuesta HTTP con el token de autenticación
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationDto authenticationDto) throws ZenBookingException {
        String token = authenticationService.authenticate(authenticationDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
