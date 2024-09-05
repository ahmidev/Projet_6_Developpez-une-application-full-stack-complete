package com.openclassrooms.mddapi.services;



import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.mappers.Mapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling user related operations.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;


    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a UserDTO object containing user details
     * @throws RuntimeException if the user with the given ID is not found
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapper.toUserDTO(user);
    }


    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user to retrieve
     * @return a UserDTO object containing user details
     * @throws RuntimeException if the user with the given email is not found
     */
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email %s ".formatted(email)));
        return mapper.toUserDTO(user);
    }


}