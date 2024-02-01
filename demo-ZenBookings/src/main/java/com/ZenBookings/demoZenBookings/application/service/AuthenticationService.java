package com.ZenBookings.demoZenBookings.application.service;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import com.ZenBookings.demoZenBookings.application.mapper.UserMapper;
import com.ZenBookings.demoZenBookings.domain.dto.AuthenticationDto;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import com.ZenBookings.demoZenBookings.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j //Generar automáticamente un registro de eventos (logging) en la clase anotada
@Service
public record AuthenticationService(
        UserRepository userRepository,
        JwtService jwtService,

        PasswordEncoder passwordEncoder,
        UserMapper mapper,
        AuthenticationManager authenticationManager
) {

    /**
     * egistra un nuevo usuario convirtiendo un DTO en una entidad, codificando la
     * contraseña, guardando el usuario en el repositorio y generando un token JWT
     * para el usuario
     *
     * @param  userDto  el objeto de transferencia de datos del usuario
     * @return          el token generado
     */

    public String register(UserDto userDto) throws ZenBookingException {
        try{
            User user = mapper.toEntity(userDto); //Convierte el objeto UserDto en un objeto User
            user.setPassword(passwordEncoder.encode(userDto.password())); //Codifica la contraseña del
            // usuario y la asigna al objeto User.
            user.setEnable(true);
            userRepository.save(user);
            return jwtService.generateToken(user);//Genera un token JWT para el usuario y lo devuelve
            // como resultado del método.
        } catch (DataIntegrityViolationException e){
            throw new ZenBookingException(EMessage.USER_EXISTS);
        }
    }

    /**
     *  toma un objeto "AuthenticationDto" como parámetro y devuelve un token de autenticación generado.
     *  Primero autentica al usuario utilizando las credenciales proporcionadas, luego recupera al usuario
     *  del repositorio basado en el correo electrónico y finalmente genera y devuelve un token JWT para el usuario
     *
     * @param  authenticationDto  la información de autenticación
     * @return                   el token de autenticación generado
     */
    public String authenticate(AuthenticationDto authenticationDto) throws ZenBookingException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.email(),
                        authenticationDto.password()
                )
        );
        User user = userRepository.findUserByEmail(authenticationDto.email())
                .orElseThrow(() -> new ZenBookingException(EMessage.INVALID_CREDENTIALS));
        return jwtService.generateToken(user);
    }
}
