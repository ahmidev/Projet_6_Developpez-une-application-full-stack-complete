package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir les entités Subscription en DTO (Data Transfer Object) et inversement.
 * Facilite la transformation des objets Subscription en SubscriptionDTO pour l'échange entre différentes couches de l'application.
 */
@Component
public class SubscriptionMapper {

    /**
     * Convertit une entité Subscription en DTO SubscriptionDTO.
     *
     * @param subscription l'entité Subscription à convertir
     * @return le SubscriptionDTO correspondant
     */
    public SubscriptionDTO toDto(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setUserId(subscription.getUser().getId());
        dto.setTopicId(subscription.getTopic().getId());
        return dto;
    }

    /**
     * Convertit une liste d'entités Subscription en une liste de SubscriptionDTO.
     *
     * @param subscriptions liste des entités Subscription à convertir
     * @return liste de SubscriptionDTO correspondante
     */
    public List<SubscriptionDTO> toDto(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convertit un DTO SubscriptionDTO en entité Subscription.
     *
     * @param dto le DTO Subscription à convertir
     * @param user l'entité User liée à l'abonnement
     * @param topic l'entité Topic liée à l'abonnement
     * @return l'entité Subscription correspondante
     */
    public Subscription toEntity(SubscriptionDTO dto, User user, Topic topic) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        return subscription;
    }
}
