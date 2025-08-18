package com.github.italoalcantaraa.todolistapi.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRETY_KEY;

    public JwtUtil() {
        Dotenv dotenv = Dotenv.load();
        this.SECRETY_KEY = dotenv.get("JWT_SECRET_KEY");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // dono do token
                .setIssuedAt(new Date()) // quando foi gerado
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // expiração do token em 10h
                .signWith(SignatureAlgorithm.HS384, SECRETY_KEY) // define o algoritmo de assinatura do token
                .compact(); // gera o token
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRETY_KEY) // chave usada para assinar o token
                .parseClaimsJws(token) // verifica se a assinatura é válida
                .getBody() // extrai as claims
                .getSubject(); // username
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRETY_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}
