package com.openclassrooms.mddapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Composant qui gère les erreurs d'authentification non autorisées.
 * Cette classe est appelée lorsque des requêtes non authentifiées tentent d'accéder à des ressources sécurisées.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    /**
     * Méthode appelée lorsque l'utilisateur tente d'accéder à une ressource sécurisée sans être authentifié.
     * Cette méthode envoie une réponse d'erreur HTTP 401 (Unauthorized).
     *
     * @param request       l'objet HttpServletRequest de la requête
     * @param response      l'objet HttpServletResponse de la réponse
     * @param authException l'exception soulevée lors de l'échec de l'authentification
     * @throws IOException si une erreur d'entrée/sortie survient lors de l'envoi de la réponse
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("Réponse avec erreur non autorisée. Message - {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non autorisé");
    }
}
