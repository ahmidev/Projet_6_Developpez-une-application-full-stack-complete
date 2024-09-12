package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;



    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/auth/me")
    public ResponseEntity<UserUpdateDTO> updateUserProfile(@RequestBody UserRegisterDTO userRegisterDTO) {
        // Récupérer l'email de l'utilisateur connecté depuis le contexte de sécurité
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userService.findByEmail(currentEmail);

        if (currentUser.isPresent()) {
            User user = currentUser.get();

            // Récupérer les informations du DTO (mot de passe facultatif)
            String newUsername = userRegisterDTO.getUsername();
            String newEmail = userRegisterDTO.getEmail();
            String newPassword = userRegisterDTO.getPassword();  // Peut être nul ou vide

            // Appeler le service pour mettre à jour l'utilisateur et potentiellement générer un nouveau token
            UserUpdateDTO updatedUser = userService.updateUserProfile(user.getId(), newUsername, newEmail, newPassword);

            return ResponseEntity.ok(updatedUser);
        } else {
            // Si l'utilisateur n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
