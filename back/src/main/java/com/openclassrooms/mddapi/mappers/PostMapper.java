package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.models.Post;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class PostMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    CommentMapper commentMapper;

    public List<PostDTO> toDto(List<Post> posts){
        List<PostDTO> postsDto = new ArrayList<>();

        for(Post post : posts){
            PostDTO postDto = toDto(post);
            postsDto.add(postDto);
        }

        return postsDto;

    }

    public PostDTO toDto(Post post){
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setComments(commentMapper.toDto(post.getComments()));
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUser(userMapper.toDto(post.getUser()));
        postDto.setTopicDTO(topicMapper.toDto(post.getTopic()));

        return postDto;
    }
}
