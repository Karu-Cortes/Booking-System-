package com.ZenBookings.demoZenBookings.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;


    private final static String[] WHITE_LIST_URL = {
            //Lista de rutas que están permitidas sin necesidad de autenticación.
            "/api/v1/auth/**",
            "/docs/api-docs/**",
            "/docs/api-docs.yaml",
            "/docs/swagger-ui/**",
            "/health",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };


    /**
     * crea una cadena de filtros de seguridad para configurar la seguridad HTTP. Deshabilita
     * CSRF y CORS, especifica reglas de autorización, gestión de sesiones, proveedores de autenticación,
     * y agrega un filtro JWT antes del UsernamePasswordAuthenticationFilter.
     *
     * @param  http	la configuración de seguridad HTTP
     * @return     	la cadena de filtros de seguridad construida
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).sessionManagement(
                        sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

