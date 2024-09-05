package com.openclassrooms.mddapi.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePostDTO {

    Long themeId;

    Long userId;

    String title;

    String content;
}
