package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface pour les opérations CRUD sur les entités Subscription.
 * Fournit des méthodes pour vérifier l'existence d'abonnements spécifiques.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    /**
     * Recherche un abonnement spécifique en fonction de l'utilisateur et du sujet.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param topicId l'identifiant du sujet
     * @return un Optional contenant l'abonnement s'il existe
     */
    Optional<Subscription> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);

    /**
     * Vérifie si un abonnement existe entre un utilisateur et un sujet.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param topicId l'identifiant du sujet
     * @return true si l'abonnement existe, false sinon
     */
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
}