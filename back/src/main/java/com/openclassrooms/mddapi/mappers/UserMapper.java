package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import org.springframework.stereotype.Component;

/**
 * Mapper pour la conversion des entités User en DTO (Data Transfer Object) et inversement.
 * Facilite la gestion des objets User dans les différentes couches de l'application.
 */
@Component
public class UserMapper {

    private final SubscriptionMapper subscriptionMapper;

    /**
     * Constructeur pour injecter le mapper SubscriptionMapper.
     *
     * @param subscriptionMapper le mapper pour gérer les abonnements (subscriptions) d'un utilisateur
     */
    public UserMapper(SubscriptionMapper subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    /**
     * Convertit un UserRegisterDTO en entité User.
     *
     * @param userRegisterDTO les informations d'enregistrement d'un utilisateur
     * @return l'entité User correspondante
     */
    public User toEntity(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        user.setName(userRegisterDTO.getUsername());
        return user;
    }

    /**
     * Convertit un UserLoginDTO en entité User.
     *
     * @param userLoginDTO les informations de connexion de l'utilisateur
     * @return l'entité User correspondante
     */
    public User toEntity(UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userLoginDTO.getEmail());
        user.setPassword(userLoginDTO.getPassword());
        return user;
    }

    /**
     * Convertit une entité User en UserDTO.
     *
     * @param user l'entité User à convertir
     * @return le DTO UserDTO correspondant
     */
    public UserDTO toDto(User user) {
        if (user == null) {
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

    /**
     * Convertit une entité User en UserUpdateDTO pour la mise à jour des informations utilisateur.
     *
     * @param user l'entité User à convertir
     * @return le DTO UserUpdateDTO correspondant
     */
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
