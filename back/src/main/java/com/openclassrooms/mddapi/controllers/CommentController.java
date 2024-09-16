package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;
import com.openclassrooms.mddapi.services.interfaces.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des commentaires.
 * Fournit un endpoint pour créer un nouveau commentaire.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructeur du contrôleur CommentController, injectant le service de gestion des commentaires.
     *
     * @param commentService service de gestion des commentaires
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * Endpoint pour créer un nouveau commentaire.
     * Cette méthode retourne le commentaire créé après une requête réussie.
     *
     * @param createCommentDto les données du commentaire à créer
     * @return une réponse contenant le commentaire créé avec un statut HTTP 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDTO> postComment(@Valid @RequestBody CreateCommentDTO createCommentDto) {
        CommentDTO commentDTO = commentService.createComment(createCommentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }
}
