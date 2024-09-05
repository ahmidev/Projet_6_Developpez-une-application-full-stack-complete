package com.openclassrooms.mddapi.services;


import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.models.AuthSuccess;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication related operations.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * Constructs an AuthService with the specified dependencies.
     *
     * @param userRepository the user repository
     * @param passwordEncoder the password encoder
     * @param tokenProvider the JWT token provider
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Registers a new user with the given registration details.
     *
     * @param userRegisterDTO the user registration data transfer object
     * @return an AuthSuccess object containing the generated JWT token
     * @throws IllegalArgumentException if the email is already in use
     */
    public AuthSuccess register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getName());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        User createdUser = userRepository.save(user);
        String token = tokenProvider.generateToken(createdUser);
        return new AuthSuccess(token);
    }


    /**
     * Logs in a user with the given login details.
     *
     * @param userLoginDTO the user login data transfer object
     * @return an AuthSuccess object containing the generated JWT token
     * @throws UsernameNotFoundException if the email or password is invalid
     */
    public AuthSuccess login(UserLoginDTO userLoginDTO) {
        User existingUser = findByEmail(userLoginDTO.getEmail());
        if (existingUser == null || !passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        String token = tokenProvider.generateToken(existingUser);
        return new AuthSuccess(token);
    }


    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return the User object if found, otherwise null
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}