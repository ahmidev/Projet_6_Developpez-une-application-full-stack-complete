package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;
import com.openclassrooms.mddapi.models.Comment;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class CommentMapper {

    @Autowired
    UserMapper userMapper;

    public Comment toEntity(CreateCommentDTO createCommentDTO){
        Comment comment = new Comment();
        comment.setContent(createCommentDTO.getContent());

        return comment;
    }
    public CommentDTO toDto(Comment comment){
        CommentDTO commentDto = new CommentDTO();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUser(userMapper.toDto(comment.getUser()));
        return commentDto;
    }

    public List<CommentDTO> toDto(List<Comment> comments){
        List<CommentDTO> commentDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentDtos.add(toDto(comment));
        }
        return commentDtos;
    }
}
