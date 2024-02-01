package com.ZenBookings.demoZenBookings.application.config;

import com.ZenBookings.demoZenBookings.application.lasting.EMessage;
import com.ZenBookings.demoZenBookings.domain.repository.UserRepository;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Devuelve un objeto UserDetailsService. Utiliza una expresión lambda para buscar un
     * usuario por correo electrónico utilizando el repositorio de usuarios.
     * @Bean indica que el método userDetailsService es un método de configuración que devuelve un
     * objeto UserDetailsService, el cual será administrado por el contenedor de Spring
     * @return          un objeto UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(EMessage.USER_NOT_FOUND.getMessage()));
    }

    /**
     * crea y configura un proveedor de autenticación utilizando DaoAuthenticationProvider en una aplicación
     * Spring. Establece el servicio de detalles del usuario y el codificador de contraseña para el
     * proveedor de autenticación y devuelve el bean del proveedor de autenticación configurado.
     *
     * @return         	el proveedor de autenticación configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Un método para crear y devolver una nueva instancia de PasswordEncoder usando BCryptPasswordEncoder.
     *
     * @return         	la nueva instancia de PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *  devuelve un AuthenticationManager basado en la AuthenticationConfiguration proporcionada
     *
     * @param  configuration  la AuthenticationConfiguration utilizada para configurar el AuthenticationManager
     * @return                el AuthenticationManager configurado basado en la AuthenticationConfiguration proporcionada
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Crea y devuelve un objeto OpenAPI personalizado. Agrega un esquema de seguridad llamado "bearerAuth"
     * a los componentes de OpenAPI, especificando que utiliza seguridad de tipo HTTP con un formato de token
     * de portador JWT.
     *
     * @return         	un objeto OpenAPI con esquemas de seguridad personalizados
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
