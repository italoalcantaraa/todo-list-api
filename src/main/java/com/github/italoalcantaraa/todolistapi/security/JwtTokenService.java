package com.github.italoalcantaraa.todolistapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    private final String SECRET_KEY;
    private static final String ISSUER = "todo-list-api"; // emissor do token

    public JwtTokenService() {
        Dotenv dotenv = Dotenv.load();
        this.SECRET_KEY = dotenv.get("SECRET_KEY");
    }
    public String generateToken(UserDetailsImpl userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER) // define o emissor do toker
                    .withIssuedAt(creationDate()) // data de criação
                    .withExpiresAt(expirationDate()) // data de expiraç]ap
                    .withSubject(userDetails.getUsername()) // define o "dono" do token
                    .sign(algorithm); // define a assinatura do token com o algoritmo especif.
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error token: ", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // emissor do token
                    .build()
                    .verify(token) // verifica a validade do token
                    .getSubject(); // obtém o "dono" do token
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error token: ", exception);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("UTC")).plusHours(4).toInstant();
    }

}
