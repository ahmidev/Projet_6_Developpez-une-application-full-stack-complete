package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    PostMapper postMapper;

    public List<PostDTO> getAllUsersSubscribedArticles(Long userId){
        List<Post> articles = postRepository.getPostsByUserID(userId);
        return postMapper.toDto(articles);
    }
    @Transactional
    public PostDTO createAnArticle(CreatePostDTO createPost){
        Post post = new Post();
        post.setContent(createPost.getContent());
        post.setTitle(createPost.getTitle());

        Optional<User> optUser = userRepository.findById(createPost.getUserId());
        Optional<Topic> optTheme = topicRepository.findById(createPost.getThemeId());
        if(optTheme.isPresent() && optUser.isPresent() ){
            post.setTheme(optTheme.get());
            post.setUser(optUser.get());
            postRepository.saveAndFlush(post);
            return postMapper.toDto(post);
        }

        throw new RuntimeException("theme inexistant");
    }
}
