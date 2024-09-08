package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    /**
     * récupère tous les articles liés à l'utilisateur connecté
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    List<PostDTO> getAllUsersSubscribedPosts(@PathVariable Long userId){

        return postService.getAllUsersSubscribedPosts(userId);
    }

    @PostMapping("/create")
    PostDTO createAnPost(@RequestBody CreatePostDTO createPost){
        return postService.createAnPost(createPost);
    }

    /**
     * récupère un article par son id
     * @param articleId
     * @return
     */
    @GetMapping("{articleId}")
    PostDTO getPostByID(@PathVariable Long articleId){

        return postService.getPostById(articleId);
    }


}


