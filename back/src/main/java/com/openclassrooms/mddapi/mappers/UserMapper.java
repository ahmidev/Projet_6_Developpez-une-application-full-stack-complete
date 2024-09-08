package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.models.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class UserMapper {

    public User toEntity(UserRegisterDTO userRegisterDTO){
        if(userRegisterDTO == null){
            return null;
        }
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        user.setName(userRegisterDTO.getUsername());

        return user;
    }

    public User toEntity(UserLoginDTO userLoginDTO){
        if(userLoginDTO == null){
            return null;
        }
        User user = new User();
        user.setEmail(userLoginDTO.getEmail());
        user.setPassword(userLoginDTO.getPassword());

        return user;
    }

    public UserDTO toDto(User user){
        if(user == null){
            return null;
        }
        UserDTO userDto = new UserDTO();

        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setSubscriptions(user.getSubscriptions());

        return userDto;
    }
}
