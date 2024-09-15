package com.openclassrooms.mddapi.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Classe de configuration de la sécurité pour l'application Spring Boot.
 * Cette configuration inclut la gestion des filtres de sécurité, la gestion des CORS
 * et l'authentification avec des jetons JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructeur de la configuration de sécurité.
     *
     * @param unauthorizedHandler le point d'entrée pour gérer les accès non autorisés
     * @param jwtAuthenticationFilter le filtre JWT pour authentifier les requêtes
     */
    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configure la chaîne de filtres de sécurité pour les requêtes HTTP.
     *
     * @param http l'objet HttpSecurity utilisé pour la configuration
     * @return l'objet SecurityFilterChain configuré
     * @throws Exception si une erreur survient lors de la configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/register").permitAll();
                    auth.requestMatchers("/api/auth/login").permitAll();
                    auth.requestMatchers("/images/**").permitAll();
                    auth.requestMatchers("/css/**", "/js/**").permitAll();
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Définit le bean pour l'encodage des mots de passe avec BCrypt.
     *
     * @return un objet BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Définit le bean pour la gestion de l'authentification.
     *
     * @param authenticationConfiguration la configuration de l'authentification
     * @return un objet AuthenticationManager
     * @throws Exception si une erreur survient lors de la configuration de l'authentification
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Définit un filtre CORS pour gérer les requêtes cross-origin.
     *
     * @return un objet CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Content-Disposition");
        config.addExposedHeader("Content-Length");
        config.addExposedHeader("Cache-Control");

        source.registerCorsConfiguration("/images/**", config);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}