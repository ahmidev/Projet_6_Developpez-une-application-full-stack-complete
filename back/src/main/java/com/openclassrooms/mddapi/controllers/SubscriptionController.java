package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.services.interfaces.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des abonnements.
 * Fournit des endpoints pour créer et supprimer des abonnements.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Constructeur du contrôleur SubscriptionController, injectant le service de gestion des abonnements.
     *
     * @param subscriptionService service de gestion des abonnements
     */
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Endpoint pour créer un nouvel abonnement.
     * Cette méthode permet à un utilisateur de s'abonner à un thème en fournissant les informations nécessaires dans le corps de la requête.
     *
     * @param subscriptionDto l'objet contenant les informations de l'abonnement (utilisateur et thème)
     * @return les détails de l'utilisateur mis à jour après l'abonnement
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createSubscription(@RequestBody SubscriptionDTO subscriptionDto) {
        return subscriptionService.createSubscription(subscriptionDto);
    }

    /**
     * Endpoint pour supprimer un abonnement.
     * Cette méthode permet à un utilisateur de se désabonner d'un thème spécifique en utilisant l'ID de l'utilisateur et l'ID du thème.
     *
     * @param userId  l'ID de l'utilisateur qui souhaite se désabonner
     * @param topicId l'ID du thème auquel l'utilisateur est abonné
     * @return les détails de l'utilisateur mis à jour après la suppression de l'abonnement
     */
    @DeleteMapping("/{userId}/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO deleteSubscription(@PathVariable long userId, @PathVariable long topicId) {
        return subscriptionService.deleteSubscription(userId, topicId);
    }

}

