package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserUpdateDTO updateUserProfile(Long userId, String newUsername, String newEmail, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }

        User user = userOptional.get();
        user.setName(newUsername);
        user.setEmail(newEmail);

        String newToken = null;
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            // Générer un nouveau token si le mot de passe est modifié

        }
        newToken = tokenProvider.generateToken(user);
        userRepository.save(user);

        UserUpdateDTO updatedUserDto = userMapper.toUpdateDto(user);

        if (newToken != null) {
            updatedUserDto.setTocken(newToken);
        }

        return updatedUserDto;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
