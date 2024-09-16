package com.openclassrooms.mddapi.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePostDTO {

    Long topicId;

    Long userId;

    String title;

    String content;
}
