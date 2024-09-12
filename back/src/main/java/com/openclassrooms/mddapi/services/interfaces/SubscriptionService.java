package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;

public interface SubscriptionService {

    UserDTO createSubscription(SubscriptionDTO subscriptionDto);

    void deleteSubscription(Long userId, Long themeId);
}

