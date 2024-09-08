package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.models.Post;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class PostMapper {

//    public List<PostDTO> toDto(List<Post> posts){
//        List<PostDTO> articlesDto = new ArrayList<>();
//
//        for(Post article : posts){
//            PostDTO articleDto = toDto(article);
//            articlesDto.add(articleDto);
//        }
//
//        return articlesDto;
//
//    }

//    public PostDTO toDto(Post post){
//        PostDTO articleDto = new PostDTO();
//        articleDto.setId(post.getId());
//        articleDto.setTitle(post.getTitle());
//        articleDto.setContent(post.getContent());
//        articleDto.setComments(post.getComments());
//        articleDto.setCreatedAt(post.getCreatedAt());
//
//
//        return articleDto;
//    }
}
