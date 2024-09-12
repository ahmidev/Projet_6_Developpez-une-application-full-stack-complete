package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.AuthSuccess;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService,UserService userService, UserMapper userMapper ) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;

    }
    @PostMapping("/register")
    public ResponseEntity<AuthSuccess> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            AuthSuccess authSuccess = authService.register(userRegisterDTO);
            return ResponseEntity.ok(authSuccess);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().body(null); // or return a custom error response
        } catch (DataIntegrityViolationException e) {
            System.out.println("Erreur: " + e.getMessage());
            return ResponseEntity.status(409).body(null); // conflict, e.g., email already in use
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            return ResponseEntity.status(500).body(null); // internal server error
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthSuccess> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            AuthSuccess authSuccess = authService.login(userLoginDTO);
            return ResponseEntity.ok(authSuccess);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthSuccess("Invalid email or password"));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String currentPrincipalName = authentication.getName();
        Optional<User> userOptional = userService.findByEmail(currentPrincipalName);
        User user = userOptional.get();
        UserDTO userDTO =   userMapper.toDto(user);
        return ResponseEntity.ok(userDTO);
    }
}
