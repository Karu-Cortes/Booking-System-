package com.ZenBookings.demoZenBookings.application.config;

import com.ZenBookings.demoZenBookings.application.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    /**
     * Verifica la presencia de un token JWT en el encabezado "Authorization"
     * de la solicitud HTTP, extrae el correo electrónico del usuario desde el token
     *     y establece los detalles de autenticación del usuario en el contexto de seguridad
     *     si el usuario no está autenticado. Si el token no está presente o no comienza con "Bearer ",
     *     el filtro pasa la solicitud sin procesarla.
     *
     * @param  request         la solicitud HTTP servlet
     * @param  response        la respuesta HTTP servlet
     * @param  filterChain     la cadena de filtros para proceder con el proceso de filtrado
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            setAuthenticationToContext(request, jwt, userDetails);
            filterChain.doFilter(request, response);
        }

    }

    //Crea un token de autenticación y lo establece en el contexto de seguridad si el token es válido
    private void setAuthenticationToContext(HttpServletRequest request, String jwt, UserDetails userDetails) {
        if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Establecer la autenticación en el contexto de seguridad, aplicando a todas las peticiones

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }


}
