package com.example.learning_management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String ACCESS_TOKEN_TYPE = "access";

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.access-token-expiration}")
    private Long accessTokenExpiration;

//    TOKEN GENERATION

    public String generateAccessToken(UserDetails userDetails) {

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return generateTokenInternal(userDetails.getUsername(), roles, accessTokenExpiration, ACCESS_TOKEN_TYPE);
    }

//    TOKEN VALIDATION

    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

// Claims Extraction

    public String extractUsername(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public String extractType(String token) {

        return extractAllClaims(token).get("type", String.class);
    }

    public Date extractExpiration(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getExpiration();

    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateTokenInternal(String username, List<String> roles, long expirationTime, String type) {

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }


    //    KEY HANDLING
    private SecretKey getSigningKey() {

        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
