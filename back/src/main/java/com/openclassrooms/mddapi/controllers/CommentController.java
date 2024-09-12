package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.CreateCommentDTO;
import com.openclassrooms.mddapi.services.interfaces.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/")
    CommentDTO postComment(@Valid @RequestBody CreateCommentDTO createCommentDto){
        return commentService.createComment(createCommentDto);
    }
}
