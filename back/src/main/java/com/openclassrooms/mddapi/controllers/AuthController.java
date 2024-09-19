package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.AuthSuccess;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.interfaces.AuthService;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Contrôleur REST pour la gestion de l'authentification.
 * Fournit des endpoints pour l'inscription, la connexion, et la récupération de l'utilisateur courant.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Constructeur du contrôleur AuthController, injectant les services et mapper nécessaires.
     *
     * @param authService service d'authentification
     * @param userService service de gestion des utilisateurs
     * @param userMapper  mapper pour transformer les entités User en DTO
     */
    public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Endpoint pour enregistrer un nouvel utilisateur.
     * Cette méthode retourne un token JWT après une inscription réussie.
     *
     * @param userRegisterDTO les données d'enregistrement de l'utilisateur
     * @return une réponse contenant le token d'authentification
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthSuccess> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        AuthSuccess authSuccess = authService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authSuccess);
    }

    /**
     * Endpoint pour connecter un utilisateur existant.
     * Cette méthode retourne un token JWT après une connexion réussie.
     *
     * @param userLoginDTO les données de connexion de l'utilisateur
     * @return une réponse contenant le token d'authentification
     */
    @PostMapping("/login")
    public ResponseEntity<AuthSuccess> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        AuthSuccess authSuccess = authService.login(userLoginDTO);
        return ResponseEntity.ok(authSuccess);
    }

    /**
     * Endpoint pour récupérer les informations de l'utilisateur actuellement authentifié.
     *
     * @return les informations de l'utilisateur actuel sous forme de DTO
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String currentPrincipalName = authentication.getName();
        Optional<User> userOptional = userService.findByEmail(currentPrincipalName);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOptional.get();
        UserDTO userDTO = userMapper.toDto(user);
        return ResponseEntity.ok(userDTO);
    }
}
