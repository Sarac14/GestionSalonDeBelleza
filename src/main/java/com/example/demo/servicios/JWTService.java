package com.example.demo.servicios;

import com.example.demo.Entidades.Rol;
import com.example.demo.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {

    @Value("${token.jwt}")
    private String SECRET;

    public String generateJwt(Usuario usuario) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(SECRET.getBytes());
        Claims claims = Jwts.claims().setSubject("subject");
        claims.put("username", usuario.getUsername());

        List<String> authorities;
        List<Rol> roles = usuario.getRoles();

        if (roles != null && !roles.isEmpty()) {
            authorities = roles.stream()
                    .map(Rol::getDescripcion)
                    .collect(Collectors.toList());
        } else {
            authorities = Collections.emptyList();
        }

        claims.put("authorities", authorities);

        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(3600); // Example: expires in 1 hour
        Date expirationDate = Date.from(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, base64EncodedSecretKey)
                .compact();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(SECRET.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(base64EncodedSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}