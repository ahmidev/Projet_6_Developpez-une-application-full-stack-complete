package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionMapper {

    public  static SubscriptionDTO toDto(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setUserId(subscription.getUser().getId());
        dto.setTopicId(subscription.getTopic().getId());
        return dto;
    }

    public static List<SubscriptionDTO> toDto(List<Subscription> subscriptions){
        List<SubscriptionDTO> subscriptionDtos = new ArrayList<>();
        for(Subscription subscription : subscriptions){
            subscriptionDtos.add(toDto(subscription));
        }
        return subscriptionDtos;
    }

    public static Subscription toEntity(SubscriptionDTO dto, User user, Topic topic) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        return subscription;
    }
}
