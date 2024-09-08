package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllUsersSubscribedPosts(Long userId);

    PostDTO createAnPost(CreatePostDTO createPost);

    PostDTO getPostById(Long postId);
}
