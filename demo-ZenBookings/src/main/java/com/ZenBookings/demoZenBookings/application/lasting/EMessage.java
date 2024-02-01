package com.ZenBookings.demoZenBookings.application.lasting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum EMessage {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Los datos del usuario no fueron encontrados"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "Los datos no fueron encontrados"),
    USER_EXISTS(HttpStatus.ALREADY_REPORTED, "El correo electrónico fue registrado previamente en la aplicación"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "El correo electrónico o la contraseña enviados son inválidos");

    private final HttpStatus status;
    private final String message;
}
