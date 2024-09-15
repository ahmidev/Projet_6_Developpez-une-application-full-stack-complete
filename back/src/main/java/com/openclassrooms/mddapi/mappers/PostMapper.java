package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir les entités Post en DTO (Data Transfer Object) et inversement.
 * Facilite la transformation des objets Post en PostDTO pour l'échange entre différentes couches de l'application.
 */
@Component
public class PostMapper {

    private final UserMapper userMapper;
    private final TopicMapper topicMapper;
    private final CommentMapper commentMapper;

    /**
     * Constructeur pour injecter les dépendances nécessaires au PostMapper.
     *
     * @param userMapper mapper pour les utilisateurs
     * @param topicMapper mapper pour les sujets (topics)
     * @param commentMapper mapper pour les commentaires
     */
    public PostMapper(UserMapper userMapper, TopicMapper topicMapper, CommentMapper commentMapper) {
        this.userMapper = userMapper;
        this.topicMapper = topicMapper;
        this.commentMapper = commentMapper;
    }

    /**
     * Convertit une liste d'entités Post en une liste de PostDTO.
     *
     * @param posts liste des entités Post à convertir
     * @return liste de PostDTO correspondante
     */
    public List<PostDTO> toDto(List<Post> posts) {
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une entité Post en PostDTO.
     *
     * @param post l'entité Post à convertir
     * @return le PostDTO correspondant
     */
    public PostDTO toDto(Post post) {
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setComments(commentMapper.toDto(post.getComments()));
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUser(userMapper.toDto(post.getUser()));
        postDto.setTopicDTO(topicMapper.toDto(post.getTopic()));

        return postDto;
    }
}
