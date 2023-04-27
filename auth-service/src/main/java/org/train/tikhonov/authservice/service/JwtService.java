package org.train.tikhonov.authservice.service;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.exception.JwtTokenIsExpired;

import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class JwtService {

    private String secretKey = "secretKey";

    private final long accessTokeTime = 10 * 60 * 1000;

    private final long refreshTokenTime = 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String createAccessToken(String username, Set<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokeTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String username, Set<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserEntity user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        try {
            return !extractClaim(token, Claims::getExpiration).before(new Date());
        } catch(JwtException | IllegalArgumentException e) {
            throw new JwtTokenIsExpired("Expired or invalid token");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(extractAllClaims(token));
    }


    private Claims extractAllClaims(String token) {

        return Jwts.parser().
                setSigningKey(secretKey).
                parseClaimsJws(token)
                .getBody();
    }
}
