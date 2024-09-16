package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Interface pour les opérations CRUD sur les entités Post.
 * Fournit également une méthode pour récupérer les posts des utilisateurs abonnés à un sujet.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Trouve les posts associés aux abonnements de l'utilisateur via les sujets auxquels ils sont abonnés.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste de posts
     */
    List<Post> findByTopic_Subscriptions_UserId(Long userId);
}