package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;

/**
 * Interface pour le service de gestion des abonnements.
 * Définit les méthodes pour créer et supprimer des abonnements pour les utilisateurs.
 */
public interface SubscriptionService {

    /**
     * Crée un nouvel abonnement pour un utilisateur à un sujet spécifique.
     *
     * @param subscriptionDto les informations de l'abonnement à créer
     * @return un DTO de l'utilisateur après la création de l'abonnement
     */
    UserDTO createSubscription(SubscriptionDTO subscriptionDto);

    /**
     * Supprime l'abonnement d'un utilisateur à un sujet spécifique.
     *
     * @param userId  l'ID de l'utilisateur
     * @param themeId l'ID du sujet (topic)
     * @return un DTO de l'utilisateur après la suppression de l'abonnement
     */
    UserDTO deleteSubscription(Long userId, Long themeId);
}
