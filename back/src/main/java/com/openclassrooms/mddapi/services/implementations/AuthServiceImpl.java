package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.models.AuthSuccess;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import com.openclassrooms.mddapi.services.interfaces.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service d'authentification.
 * Fournit les méthodes pour enregistrer et connecter les utilisateurs.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param userRepository le repository pour gérer les utilisateurs
     * @param passwordEncoder l'encodeur de mot de passe pour sécuriser les mots de passe
     * @param tokenProvider le fournisseur de jeton JWT pour générer les tokens
     */
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthSuccess register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        User createdUser = userRepository.save(user);
        String token = tokenProvider.generateToken(createdUser);
        return new AuthSuccess(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthSuccess login(UserLoginDTO userLoginDTO) {
        User existingUser = findByEmail(userLoginDTO.getEmail());
        if (existingUser == null || !passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
            throw new UsernameNotFoundException("Email ou mot de passe invalide");
        }
        String token = tokenProvider.generateToken(existingUser);
        return new AuthSuccess(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
