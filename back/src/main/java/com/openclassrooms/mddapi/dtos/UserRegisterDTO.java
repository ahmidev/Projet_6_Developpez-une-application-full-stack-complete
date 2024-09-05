package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String name;
    @NotBlank
    private String password;


}