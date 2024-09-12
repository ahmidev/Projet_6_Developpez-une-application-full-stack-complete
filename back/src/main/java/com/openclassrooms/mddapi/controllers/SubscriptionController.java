package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.services.interfaces.SubscriptionService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    UserDTO createTopic(@RequestBody SubscriptionDTO subscriptionDto) {
        return subscriptionService.createSubscription(subscriptionDto);
    }

    @DeleteMapping("/delete/{userId}/{themeId}")
    UserDTO deleteTopic(@PathVariable long userId, @PathVariable long themeId) {
       return subscriptionService.deleteSubscription(userId, themeId);
    }

}
