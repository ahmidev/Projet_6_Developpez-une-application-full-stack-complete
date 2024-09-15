package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.TopicDTO;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir les entités Topic en DTO (Data Transfer Object) et inversement.
 * Facilite la transformation des objets Topic en TopicDTO pour l'échange entre différentes couches de l'application.
 */
@Component
public class TopicMapper {

    private final SubscriptionMapper subscriptionMapper;

    /**
     * Constructeur pour injecter le mapper SubscriptionMapper.
     *
     * @param subscriptionMapper le mapper pour convertir les abonnements (subscriptions)
     */
    public TopicMapper(SubscriptionMapper subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    /**
     * Convertit une liste d'entités Topic en une liste de DTO TopicDTO.
     *
     * @param topics la liste des entités Topic à convertir
     * @return une liste de TopicDTO correspondante
     */
    public List<TopicDTO> toDto(List<Topic> topics) {
        return topics.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une entité Topic en DTO TopicDTO.
     *
     * @param topic l'entité Topic à convertir
     * @return le DTO TopicDTO correspondant
     */
    public TopicDTO toDto(Topic topic) {
        TopicDTO topicDto = new TopicDTO();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        topicDto.setDescription(topic.getDescription());
        topicDto.setSubscriptions(subscriptionMapper.toDto(topic.getSubscriptions()));  // Convertit les abonnements

        return topicDto;
    }
}
