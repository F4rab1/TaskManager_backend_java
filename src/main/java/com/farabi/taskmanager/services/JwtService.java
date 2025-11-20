package com.farabi.taskmanager.services;

import com.farabi.taskmanager.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user) {
        final long tokenExpiration = 60 * 5; // 5 minutes

        return generateToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user) {
        final long tokenExpiration = 604800; // 7 days

        return generateToken(user, tokenExpiration);
    }

    public String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);

            return claims.getExpiration().after(new Date());
        }
        catch (Exception ex) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
