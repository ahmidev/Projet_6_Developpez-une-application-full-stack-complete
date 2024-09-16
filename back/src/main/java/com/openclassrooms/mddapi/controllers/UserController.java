package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Fournit des endpoints pour la mise à jour du profil de l'utilisateur connecté.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {


    private final UserService userService;

    /**
     * Constructeur du contrôleur UserController, injectant le service de gestion des utilisateurs.
     *
     * @param userService service de gestion des utilisateurs
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Endpoint pour mettre à jour le profil de l'utilisateur actuellement authentifié.
     * Récupère l'utilisateur à partir du contexte de sécurité, puis met à jour son profil avec les nouvelles informations fournies.
     *
     * @param userRegisterDTO les nouvelles informations de l'utilisateur (nom, email, mot de passe)
     * @return ResponseEntity contenant l'objet UserUpdateDTO mis à jour et un statut HTTP 200 OK, ou un statut 404 si l'utilisateur n'est pas trouvé
     */
    @PutMapping("/auth/me")
    public ResponseEntity<UserUpdateDTO> updateUserProfile(@RequestBody UserRegisterDTO userRegisterDTO) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userService.findByEmail(currentEmail);

        if (currentUser.isPresent()) {
            User user = currentUser.get();

            UserUpdateDTO updatedUser = userService.updateUserProfile(
                    user.getId(),
                    userRegisterDTO.getUsername(),
                    userRegisterDTO.getEmail(),
                    userRegisterDTO.getPassword()
            );

            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
