package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Implémentation du service de gestion des commentaires.
 * Cette classe fournit des méthodes pour créer des commentaires sur des posts.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param commentMapper     le mapper pour convertir les entités Comment en DTO et inversement
     * @param commentRepository le repository pour gérer les commentaires
     * @param postRepository    le repository pour gérer les posts
     * @param userRepository    le repository pour gérer les utilisateurs
     */
    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Crée un nouveau commentaire sur un post existant.
     *
     * @param createCommentDto les informations du commentaire à créer
     * @return le DTO du commentaire créé
     * @throws ResponseStatusException si l'utilisateur ou le post n'existent pas
     */
    public CommentDTO createComment(CreateCommentDTO createCommentDto) {
        Comment comment = commentMapper.toEntity(createCommentDto);

        Optional<User> optionalUser = userRepository.findById(createCommentDto.getUserId());
        Optional<Post> optionalArticle = postRepository.findById(createCommentDto.getArticleId());

        if (optionalArticle.isPresent() && optionalUser.isPresent()) {
            comment.setUser(optionalUser.get());
            comment.setPost(optionalArticle.get());
            commentRepository.saveAndFlush(comment);

            return commentMapper.toDto(comment);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erreur dans la récuperation de l'article ou du user");
    }
}
