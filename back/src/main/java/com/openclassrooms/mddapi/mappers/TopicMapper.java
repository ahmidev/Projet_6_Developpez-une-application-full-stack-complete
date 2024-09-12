package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.TopicDTO;
import com.openclassrooms.mddapi.models.Topic;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class TopicMapper {

    @Autowired
    SubscriptionMapper subscriptionMapper;

    public List<TopicDTO> toDto(List<Topic> topics){
        List<TopicDTO> topicsDto = new ArrayList<>();

        for(Topic topic : topics){
            TopicDTO topicDto = toDto(topic);
            topicsDto.add(topicDto);
        }

        return topicsDto;

    }

    public TopicDTO toDto(Topic topic){
        TopicDTO topicDto = new TopicDTO();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        topicDto.setDescription(topic.getDescription());
        topicDto.setSubscriptions(subscriptionMapper.toDto(topic.getSubscriptions()));

        return topicDto;
    }
}
