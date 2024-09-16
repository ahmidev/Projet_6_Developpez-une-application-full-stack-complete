package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;
import com.openclassrooms.mddapi.models.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir les entités Comment en DTO (Data Transfer Object) et inversement.
 * Facilite la transformation des objets Comment en CommentDTO pour l'échange entre différentes couches de l'application.
 */
@Component
public class CommentMapper {

    private final UserMapper userMapper;

    /**
     * Constructeur pour injecter les dépendances nécessaires au CommentMapper.
     *
     * @param userMapper mapper pour les utilisateurs
     */
    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * Convertit un objet CreateCommentDTO en entité Comment.
     *
     * @param createCommentDTO l'objet DTO contenant les informations nécessaires à la création d'un commentaire
     * @return l'entité Comment correspondante
     */
    public Comment toEntity(CreateCommentDTO createCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(createCommentDTO.getContent());
        return comment;
    }

    /**
     * Convertit une entité Comment en CommentDTO.
     *
     * @param comment l'entité Comment à convertir
     * @return le CommentDTO correspondant
     */
    public CommentDTO toDto(Comment comment) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUser(userMapper.toDto(comment.getUser()));
        return commentDto;
    }

    /**
     * Convertit une liste d'entités Comment en une liste de CommentDTO.
     *
     * @param comments liste des entités Comment à convertir
     * @return liste de CommentDTO correspondante
     */
    public List<CommentDTO> toDto(List<Comment> comments) {
        // Utilisation de Stream API pour convertir chaque Comment en CommentDTO
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
