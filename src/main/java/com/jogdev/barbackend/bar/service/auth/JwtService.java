package com.jogdev.barbackend.bar.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;


    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extraClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String jwt) {
        return extraClaims(jwt).getSubject();
    }

    private Date extractExpiration(String jwt) {
        return extraClaims(jwt).getExpiration();
    }

    private boolean isTokenExpired(String jwt) {


        return extractExpiration(jwt).before(new Date());
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }
}
