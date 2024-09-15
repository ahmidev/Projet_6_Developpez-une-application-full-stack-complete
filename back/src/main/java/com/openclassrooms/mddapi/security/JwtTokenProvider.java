package com.openclassrooms.mddapi.security;


import com.openclassrooms.mddapi.models.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Classe responsable de la génération, de la validation et de l'extraction d'informations
 * à partir des jetons JWT (JSON Web Token).
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Génère un JWT pour un utilisateur donné.
     *
     * @param user l'utilisateur pour lequel le jeton est généré
     * @return le jeton JWT généré
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Extrait l'adresse e-mail de l'utilisateur à partir du JWT.
     *
     * @param token le jeton JWT
     * @return l'adresse e-mail extraite du JWT
     */
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    /**
     * Valide le JWT pour s'assurer qu'il est bien formé et non expiré.
     *
     * @param authToken le jeton JWT à valider
     * @return true si le jeton est valide, false sinon
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature", ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token", ex);
        } catch (Exception ex) {
            logger.error("JWT token validation failed", ex);
        }
        return false;
    }
}
