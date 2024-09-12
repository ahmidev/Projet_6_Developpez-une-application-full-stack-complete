package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;

public interface CommentService {

    CommentDTO createComment(CreateCommentDTO createCommentDto);
}
