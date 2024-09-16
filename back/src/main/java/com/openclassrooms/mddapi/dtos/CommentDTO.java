package com.openclassrooms.mddapi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private String content;

    private UserDTO user;
}
