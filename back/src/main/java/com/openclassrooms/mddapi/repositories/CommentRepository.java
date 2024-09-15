package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface pour les opérations CRUD sur les entités Commentaire.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}