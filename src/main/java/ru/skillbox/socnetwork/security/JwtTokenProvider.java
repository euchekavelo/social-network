package ru.skillbox.socnetwork.security;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Repository
public class JwtTokenProvider {

    @Getter
    @Value("${skillbox.app.jwtSecret}")
    private String secret;

    @Getter
    @Value("${skillbox.app.jwtExpirationMin}")
    private int expiration;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date exp = Date.from(LocalDateTime.now().plusMinutes(expiration)
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * TODO check method of getting email from token
     */
    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            /**
             * TODO do something with logout after token expired
             */
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
