package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.models.AuthSuccess;
import com.openclassrooms.mddapi.models.User;

/**
 * Interface pour le service d'authentification.
 * Définit les méthodes pour gérer l'enregistrement et la connexion des utilisateurs.
 */
public interface AuthService {

    /**
     * Enregistre un nouvel utilisateur avec les détails fournis lors de l'inscription.
     *
     * @param userRegisterDTO les informations d'inscription de l'utilisateur
     * @return un objet AuthSuccess contenant le token JWT généré
     * @throws IllegalArgumentException si l'email est déjà utilisé
     */
    AuthSuccess register(UserRegisterDTO userRegisterDTO);

    /**
     * Connecte un utilisateur avec les détails de connexion fournis.
     *
     * @param userLoginDTO les informations de connexion de l'utilisateur
     * @return un objet AuthSuccess contenant le token JWT généré
     * @throws UsernameNotFoundException si l'email ou le mot de passe est invalide
     */
    AuthSuccess login(UserLoginDTO userLoginDTO);

    /**
     * Recherche un utilisateur par son adresse email.
     *
     * @param email l'adresse email à rechercher
     * @return l'objet User correspondant, sinon null
     */
    User findByEmail(String email);
}
