package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service //Se scanne como servicio
public class TokenService {  //CLASE ENCARGADA DE GENERAR LOS TOKENS

    @Value("${api.security.token.secret}") //value de bean spring //obtener el valor de mi propiedad que quiero extraer el valor
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Algoritmo HMAC256 pide un string o arreglo de bytes, secret para validar la firma
            return JWT.create()
                    .withIssuer("API Voll.med") //issuer el que emite el jwt
                    .withSubject(usuario.getLogin()) //usuario a quien va dirigido
                    .withClaim("id", usuario.getId()) //si la app cliente necesita conocer el id del usuario
                    .withExpiresAt(generarFechaExpiracion()) //para hacer que el token expire en 2 horas
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("error al generar el  token jwt", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null){
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //validando la firma
            verifier = JWT.require(algorithm)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("Verify invalido");
        }
        return verifier.getSubject();
    }


    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }
}
