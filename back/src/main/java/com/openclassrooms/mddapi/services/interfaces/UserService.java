package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.models.User;

import java.util.Optional;

/**
 * Interface pour le service de gestion des utilisateurs.
 * Définit les méthodes pour mettre à jour le profil d'un utilisateur, rechercher un utilisateur par email et récupérer un utilisateur par ID.
 */
public interface UserService {

    /**
     * Met à jour le profil d'un utilisateur avec les nouvelles informations fournies.
     *
     * @param userId l'ID de l'utilisateur à mettre à jour
     * @param newUsername le nouveau nom d'utilisateur
     * @param newEmail la nouvelle adresse email
     * @param newPassword le nouveau mot de passe (facultatif)
     * @return un DTO contenant les informations mises à jour de l'utilisateur
     */
    UserUpdateDTO updateUserProfile(Long userId, String newUsername, String newEmail, String newPassword);

    /**
     * Recherche un utilisateur par son adresse email.
     *
     * @param email l'adresse email de l'utilisateur
     * @return un Optional contenant l'utilisateur s'il est trouvé, sinon un Optional vide
     */
    Optional<User> findByEmail(String email);

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id l'ID de l'utilisateur à récupérer
     * @return un DTO contenant les informations de l'utilisateur
     */
    UserDTO getUserById(Long id);
}
