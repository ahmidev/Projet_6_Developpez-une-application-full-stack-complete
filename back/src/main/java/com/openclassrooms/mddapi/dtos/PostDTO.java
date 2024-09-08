package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private UserDTO user;

    private TopicDTO topicDTO;
    private List<CommentDTO> comments;
    private Instant createdAt;
}
