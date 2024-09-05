package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;


}
