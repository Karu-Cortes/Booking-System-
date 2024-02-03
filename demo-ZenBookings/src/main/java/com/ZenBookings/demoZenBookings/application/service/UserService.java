package com.ZenBookings.demoZenBookings.application.service;
import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import com.ZenBookings.demoZenBookings.application.mapper.UserMapper;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import com.ZenBookings.demoZenBookings.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public record UserService(
        UserRepository userRepository,
        UserMapper mapper
) {
    /**
     * Registra un nuevo usuario  tomando un objeto UserDto como entrada.
     * Convierte el UserDto a una entidad User usando un mapper y luego lo
     * guarda usando un userRepository
     *
     * @param  userDto  el objeto de transferencia de datos de usuario a registrar
     */
    public void registerUser(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        userRepository.save(user);
    }

    /**
     * recupera una lista paginada de usuarios de un repositorio.
     * Toma los parámetros offset y limit para especificar la posición de inicio
     * y el número máximo de usuarios a recuperar.
     *
     * @param  offset   el desplazamiento del primer usuario a devolver
     * @param  limit    el número máximo de usuarios a devolver
     * @return          una lista de UserDto que contiene a los usuarios
     */
    public List<UserDto> findAllUser(Integer offset, Integer limit) throws ZenBookingException {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<User> spas = userRepository.findAll(pageable);
        if (spas.getContent().isEmpty()) {
            throw new ZenBookingException(EMessage.DATA_NOT_FOUND);
        }
        return mapper.toDtoList(spas.getContent());
    }

    /**
     * encontrar un usuario por su ID. Toma un ID como entrada y devuelve un
     * objeto UserDto que representa al usuario encontrado
     *
     * @param  id  el ID del usuario a encontrar
     * @return     el objeto UserDto que representa al usuario encontrado
     */
    public UserDto findUserById(Integer id) throws ZenBookingException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        return mapper.toDto(user);
    }

    /**
     * toma un id y un objeto UserDto como parámetros. Intenta encontrar un usuario en el
     * repositorio con el id dado, y si no lo encuentra, lanza una ZenBookingException con el
     * mensaje "DATA_NOT_FOUND". Luego mapea el UserDto a un objeto User y lo guarda en el repositorio.
     *
     * @param  id       el ID del usuario a editar
     * @param  userDto  el UserDto que contiene la información actualizada del usuario
     * @throws ZenBookingException si no se encuentra el usuario
     */


    public void editUser(Integer id, UserDto userDto) throws ZenBookingException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));

        if (userDto.name() != null) {
            existingUser.setName(userDto.name());
        }
        if (userDto.email() != null) {
            existingUser.setEmail(userDto.email());
        }
        if (userDto.password() != null) {
            existingUser.setPassword(userDto.password());
        }
        if (userDto.role() != null) {
            existingUser.setRole(userDto.role());
        }
        if (userDto.enable() != null) {
            existingUser.setEnable(userDto.enable());
        }

        userRepository.save(existingUser);
    }


    /**
     * toma un parámetro id de tipo Integer. Intenta encontrar un objeto User en el
     * userRepository con el id dado y, si no lo encuentra, lanza una ZenBookingException
     * con el mensaje DATA_NOT_FOUND. Si el usuario se encuentra, se elimina del userRepository
     *
     * @param  id   el ID del usuario a eliminar
     * @throws ZenBookingException  si el usuario no se encuentra
     */

    public void removeUser(Integer id) throws ZenBookingException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ZenBookingException(EMessage.DATA_NOT_FOUND));
        userRepository.delete(user);
    }

}
