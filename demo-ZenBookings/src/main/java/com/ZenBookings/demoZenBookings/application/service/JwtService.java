package com.ZenBookings.demoZenBookings.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public record JwtService(
        @Value("${application.security.jwt.secret-key}") String secretKey,
        @Value("${application.security.jwt.expiration}") Long jwtExpiration) {

    /**
     * Genera un token para los detalles de usuario dados.
     *
     * @param  userDetails  los detalles de usuario para la generación del token
     * @return         	el token generado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token con las reclamaciones adicionales y los detalles del usuario dados.
     *
     * @param  extraClaims   un mapa de reclamaciones adicionales para el token
     * @param  userDetails    los detalles del usuario para el token
     * @return               el token generado
     */
    private String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     *  construye un token con reclamos adicionales, detalles de usuario y tiempo de expiración. Utiliza la clase
     *  Jwts de la biblioteca io.jsonwebtoken para construir el token e incluye los reclamos adicionales,
     *  detalles de usuario y tiempo de expiración especificados. El token se firma con una clave utilizando
     *  el algoritmo HS256 y se devuelve como una cadena.
     *
     * @param  extraClaims  un mapa de reclamos adicionales para incluir en el token
     * @param  userDetails  los detalles del usuario para quien se está construyendo el token
     * @param  expiration   el tiempo de expiración del token en milisegundos
     * @return              el token construido como una cadena
     */
    private String buildToken(
            HashMap<String, Object> extraClaims,
            UserDetails userDetails,
            Long expiration
    ) {
        return Jwts.builder() //nuevo builder de tokens utilizando la clase Jwts
                .setClaims(extraClaims)//mapa de claims
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(
                        LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
                        //echa de emisión del token como la fecha y hora actuales
                ))
                .setExpiration(Date.from(
                        LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().plusMillis(expiration)
                        //fecha de expiración del token como la fecha y hora actuales
                ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                //firma el token con una clave utilizando el algoritmo de firma HS256.
                .compact(); //compacta el token y se devuelve como una cadena.
    }

    /**
     * extractUserName que toma un token como entrada. Este método utiliza el método extractClaim para extraer el
     * nombre de usuario del token. La extracción se realiza utilizando la función Claims::getSubject.
     * Cuando se llama al método extractUserName con un token como argumento, devuelve el nombre de usuario
     * extraído del token
     * Extrae el nombre de usuario del token.
     *
     * @param  token  el token del cual extraer el nombre de usuario
     * @return       el nombre de usuario extraído del token
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Extrae un reclamo del token usando la función de resolución de reclamos proporcionada.
     *
     * @param  token           el token del cual extraer el reclamo
     * @param  claimsResolver  la función para resolver los reclamos
     * @return                el reclamo extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        // claimsResolver para aplicarla a los reclamos extraídos y devolver un resultado.
        return claimsResolver.apply(claims);

    }

    /**
     * Extrae todas las reclamaciones del token dado.
     *
     * @param  token  el token del cual extraer las reclamaciones
     * @return       las reclamaciones extraídas
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()//construye tokens JWT utilizando la biblioteca JWT.
                .setSigningKey(getSignKey())//establece la clave de firma para el analizador JWT utilizando un método
                // llamado getSignKey().
                .build()//construye el analizador con la configuración especificada.
                .parseClaimsJws(token)//analiza el token JWT y devuelve un objeto Jws
                .getBody(); //recupera el cuerpo del objeto  y lo devuelve como resultado del método.
    }
    /**
     * devuelve una clave de firma. Decodifica una clave secreta codificada en base64 y la utiliza
     * para crear una clave de firma basada en HMAC
     *
     * @return         la clave de firma
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * toma un token y detalles del usuario como entrada, y devuelve true si el token es válido para el usuario dado,
     * de lo contrario devuelve false
     *
     * @param  token          el token a verificar
     * @param  userDetails    los detalles del usuario
     * @return               true si el token es válido, false de lo contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        //Compara el userName con el nombre de usuario en el objeto userDetails usando el método equals.
        //Verifica si el token no ha expirado llamando al método isTokenExpired con el token como argumento.
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

     /**
     * toma un token como entrada y verifica si ha expirado comparando su tiempo de expiración
      * con el tiempo actual. Devuelve true si el token ha expirado, y false en caso contrario
      *
      *  @param  token  el token a comprobar si ha expirado
    * @return       true si el token ha expirado, false en caso contrario
    */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(LocalDateTime.now());
    }

    /**
     * extraer la fecha de caducidad de un token. Toma un token como entrada y devuelve la fecha
     * de caducidad como un objeto LocalDateTime..
     *
     * @param  token  el token del cual extraer la fecha de caducidad
     * @return        la fecha de caducidad como un objeto LocalDateTime
     */
    private LocalDateTime extractExpiration(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        //se convierte el objeto Date a un Instant, se le asigna una zona horaria por defecto y finalmente se convierte
        // a un objeto LocalDateTime que se devuelve como resultado del método.
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
