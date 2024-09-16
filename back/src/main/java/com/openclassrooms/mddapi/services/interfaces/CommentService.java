package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;

/**
 * Interface pour le service de gestion des commentaires.
 * Définit les méthodes liées à la création des commentaires.
 */
public interface CommentService {

    /**
     * Crée un nouveau commentaire sur un post existant.
     *
     * @param createCommentDto les informations du commentaire à créer
     * @return le DTO du commentaire créé
     */
    CommentDTO createComment(CreateCommentDTO createCommentDto);
}
