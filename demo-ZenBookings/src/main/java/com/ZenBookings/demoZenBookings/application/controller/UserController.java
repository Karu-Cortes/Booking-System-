package com.ZenBookings.demoZenBookings.application.controller;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.service.UserService;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public record UserController(
        UserService userService
) {

    /**
     * registra un nuevo usuario utilizando los datos proporcionados. Toma un objeto UserDto
     * como parámetro y devuelve un ResponseEntity con el código de estado HTTP 201 (CREADO).
     * La anotación @PostMapping indica que este método debe ser llamado cuando se realiza una
     * solicitud POST al endpoint especificado. La anotación @SecurityRequirement especifica que
     * la solicitud debe estar autenticada utilizando un token de portador.
     *
     * @param  userDto   los datos del usuario a registrar
     * @return           la entidad de respuesta con el código de estado HTTP 201 (CREADO)
     */
    @PostMapping
    @SecurityRequirement(name = "bearerAuth") //indica que se requiere autenticación mediante un token de tipo "bearer"
    // para acceder a este método.
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * utiliza la anotación @GetMapping de Spring para manejar solicitudes HTTP GET en el punto final especificado.
     * Toma dos variables de ruta offset y limit, recupera una lista de usuarios de un servicio y devuelve la lista
     * en un ResponseEntity con el código de estado HTTP 302 (FOUND). El método también está anotado
     * con @SecurityRequirement para especificar los requisitos de seguridad para acceder a él.
     *
     * @param  offset	El offset para la obtención de usuarios
     * @param  limit	El límite de usuarios a obtener
     * @return         	Entidad de respuesta que contiene la lista de usuarios y el estado HTTP
     */
    @GetMapping("/{offset}/{limit}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findAllUser(
            //diseñado para manejar paginación de resultados. El offset especifica el número de resultados
            // que se deben omitir al principio, y el limit especifica el número máximo de resultados que se
            // deben devolver en la respuesta
            @PathVariable("offset") Integer offset,
            @PathVariable("limit") Integer limit) throws ZenBookingException {
        List<UserDto> users = userService.findAllUser(offset, limit);
        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    /**
     * Utiliza un mapeo de solicitud GET con una variable de ruta para el ID de usuario.
     * También incluye un requisito de seguridad para la autenticación de portador.
     * El método devuelve un ResponseEntity que contiene el usuario y un estado HTTP de 302 (FOUND).
     *
     * @param  id  el ID del usuario a encontrar
     * @return     un ResponseEntity que contiene el usuario y el estado HTTP
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findUserById(@PathVariable("id") Integer id) throws ZenBookingException {
        UserDto user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    /**
     * maneja peticiones HTTP PUT en el punto final /{id}. Espera una variable de ruta id y un cuerpo de solicitud
     * de tipo UserDto. Luego llama al método editUser del userService y devuelve una respuesta con el código de
     * estado HTTP 204 (NO_CONTENT). La anotación @SecurityRequirement especifica que el punto final requiere un
     * token de portador para la autenticación.
     *
     * @param  id      el ID del usuario a editar
     * @param  userDto los datos para actualizar el usuario
     * @return         una entidad de respuesta con estado sin contenido
     */

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> editUser(@PathVariable("id") Integer id, @RequestBody UserDto userDto)
            throws ZenBookingException {
        userService.editUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * tiliza la anotación @DeleteMapping para mapear las solicitudes HTTP de tipo DELETE a este método.
     *También incluye una anotación @SecurityRequirement para la autenticación con token de portador.
     * El método llama al método userService.removeUser y devuelve un ResponseEntity con un estado de NO_CONTENT.
     *
     * @param  id    el ID del usuario a eliminar
     * @return       un ResponseEntity que indica el éxito de la eliminación
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> removeUser(@PathVariable("id") Integer id) throws ZenBookingException {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
