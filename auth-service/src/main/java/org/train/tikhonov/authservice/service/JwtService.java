package org.train.tikhonov.authservice.service;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.exception.JwtTokenIsExpired;

import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class JwtService {

    private String secretKey = "secretKey";

    private final long accessTokeTime = 10 * 60 * 1000;


    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String createToken(String email, Set<String> roles, UUID userId) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokeTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserEntity user) {
        String username = extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
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
