package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/articles")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("")
    List<PostDTO> getAllUsersSubscribedArticles(@PathVariable Long userId){

        return postService.getAllUsersSubscribedArticles(userId);
    }

    @PostMapping("/create")
    PostDTO createAnArticle(@RequestBody CreatePostDTO createArticle){
        return postService.createAnArticle(createArticle);
    }


}