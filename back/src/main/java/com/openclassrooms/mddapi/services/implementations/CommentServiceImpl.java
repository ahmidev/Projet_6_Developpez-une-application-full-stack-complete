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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public CommentDTO createComment(CreateCommentDTO createCommentDto){
        Comment comment = commentMapper.toEntity(createCommentDto);

        Optional<User> optionalUser = userRepository.findById(createCommentDto.getUserId());
        Optional<Post> optionalArticle = postRepository.findById(createCommentDto.getArticleId());

        if(optionalArticle.isPresent() && optionalUser.isPresent()){
            comment.setUser(optionalUser.get());
            comment.setPost(    optionalArticle.get());
            commentRepository.saveAndFlush(comment);

            return commentMapper.toDto(comment);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"erreur dans la récuperation de l'article ou du user");
    }
}
