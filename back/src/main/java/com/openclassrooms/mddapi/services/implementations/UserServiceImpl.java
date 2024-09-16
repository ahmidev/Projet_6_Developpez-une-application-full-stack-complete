package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Implémentation du service utilisateur.
 * Cette classe fournit des méthodes pour la gestion des utilisateurs, telles que la récupération des utilisateurs par email ou ID,
 * la mise à jour des profils d'utilisateurs, et la gestion de l'authentification utilisateur.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param userRepository le repository pour gérer les utilisateurs
     * @param userMapper le mapper pour convertir les entités utilisateur en DTO
     * @param tokenProvider le fournisseur de jeton JWT pour générer des tokens d'authentification
     * @param passwordEncoder l'encodeur de mot de passe pour sécuriser les mots de passe
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Met à jour le profil de l'utilisateur, incluant son nom, son email et éventuellement son mot de passe.
     * Si le mot de passe est mis à jour, un nouveau token JWT est généré.
     *
     * @param userId l'ID de l'utilisateur à mettre à jour
     * @param newUsername le nouveau nom d'utilisateur
     * @param newEmail le nouvel email de l'utilisateur
     * @param newPassword le nouveau mot de passe de l'utilisateur (peut être null)
     * @return un DTO de l'utilisateur mis à jour
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserUpdateDTO updateUserProfile(Long userId, String newUsername, String newEmail, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        user.setName(newUsername);
        user.setEmail(newEmail);

        String newToken = null;
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            newToken = tokenProvider.generateToken(user);
        }

        userRepository.save(user);
        UserUpdateDTO updatedUserDto = userMapper.toUpdateDto(user);
        if (newToken != null) {
            updatedUserDto.setToken(newToken);
        }

        return updatedUserDto;
    }


    /**
     * Recherche un utilisateur par son adresse email.
     *
     * @param email l'email de l'utilisateur
     * @return un objet Optional contenant l'utilisateur s'il est trouvé
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id l'ID de l'utilisateur à récupérer
     * @return un DTO de l'utilisateur correspondant
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }


    /**
     * Charge les détails de l'utilisateur par son nom d'utilisateur (email) pour l'authentification.
     *
     * @param email l'email de l'utilisateur
     * @return les détails de l'utilisateur pour Spring Security
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


    /**
     * Récupère un utilisateur par son ID pour l'authentification.
     *
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur correspondant
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
