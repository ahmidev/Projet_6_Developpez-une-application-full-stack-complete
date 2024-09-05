package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllUsersSubscribedArticles(Long userId);

    PostDTO createAnArticle(CreatePostDTO createArticle);
}
