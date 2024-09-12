package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;

import java.util.Optional;

public interface UserService {

    UserUpdateDTO updateUserProfile(Long userId, String newUsername, String newEmail, String newPassword);
    Optional<User> findByEmail(String email);

}
