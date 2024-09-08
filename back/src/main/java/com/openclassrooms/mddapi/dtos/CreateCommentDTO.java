package com.openclassrooms.mddapi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCommentDTO {

    Long articleId;

    Long userId;

    String content;
}
