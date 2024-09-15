package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre pour authentifier les requêtes HTTP à l'aide d'un jeton JWT.
 * Cette classe vérifie le JWT dans l'en-tête de la requête et authentifie l'utilisateur si le jeton est valide.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param tokenProvider le fournisseur de jeton JWT
     * @param userDetailsService le service de gestion des utilisateurs
     */
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, @Lazy UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filtre les requêtes HTTP pour vérifier et valider le JWT.
     * Si le jeton est valide, l'utilisateur est authentifié dans le contexte de sécurité de Spring.
     *
     * @param request l'objet HttpServletRequest de la requête
     * @param response l'objet HttpServletResponse de la réponse
     * @param filterChain la chaîne des filtres
     * @throws ServletException si une erreur survient lors du traitement de la requête
     * @throws IOException si une erreur d'entrée/sortie survient lors du traitement de la requête
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String userEmail = tokenProvider.getUserEmailFromJWT(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException ex) {
            logger.warn("Jeton JWT expiré", ex);
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            logger.error("Impossible de définir l'authentification de l'utilisateur dans le contexte de sécurité", ex);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extrait le jeton JWT de la requête HTTP.
     *
     * @param request l'objet HttpServletRequest de la requête
     * @return le jeton JWT ou null si le jeton n'est pas présent ou mal formé
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
