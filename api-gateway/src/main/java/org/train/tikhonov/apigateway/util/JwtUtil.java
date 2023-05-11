package org.train.tikhonov.apigateway.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.train.tikhonov.apigateway.exception.JwtTokenException;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private String secretKey = "secretKey";


    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String extractUserUUIDFromToken(String token) {
        return (String)extractClaim(token, claims -> claims.get("userId"));
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        } catch(JwtException | IllegalArgumentException e) {
            throw new JwtTokenException("Incorrect token");
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
