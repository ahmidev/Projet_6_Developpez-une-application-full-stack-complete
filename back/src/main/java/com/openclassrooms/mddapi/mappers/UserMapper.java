package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@NoArgsConstructor
@Component
public class UserMapper {

    @Autowired
    SubscriptionMapper subscriptionMapper;

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
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setSubscriptions(subscriptionMapper.toDto(user.getSubscriptions()));


        return userDto;
    }

    public UserUpdateDTO toUpdateDto(User user) {
        if (user == null) {
            return null;
        }

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setId(user.getId());
        userUpdateDTO.setEmail(user.getEmail());
        userUpdateDTO.setName(user.getName());
        userUpdateDTO.setCreatedAt(user.getCreatedAt());
        userUpdateDTO.setUpdatedAt(user.getUpdatedAt());
        userUpdateDTO.setSubscriptions(subscriptionMapper.toDto(user.getSubscriptions()));



        return userUpdateDTO;
    }
}
