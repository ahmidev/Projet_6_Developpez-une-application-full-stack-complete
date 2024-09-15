package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface pour les opérations CRUD sur les entités Utilisateur.
 * Fournit des méthodes pour rechercher un utilisateur par email et vérifier l'existence d'un email.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Recherche un utilisateur par son email.
     *
     * @param email l'adresse email de l'utilisateur
     * @return un Optional contenant l'utilisateur s'il existe
     */
    Optional<User> findByEmail(String email);


    /**
     * Vérifie si un utilisateur avec l'email donné existe déjà.
     *
     * @param email l'adresse email à vérifier
     * @return true si l'utilisateur existe, false sinon
     */
    boolean existsByEmail(String email);
}