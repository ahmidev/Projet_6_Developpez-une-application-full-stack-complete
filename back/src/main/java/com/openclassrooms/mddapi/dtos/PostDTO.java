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
    private List<Comment> comments;
    private Instant createdAt;
}
