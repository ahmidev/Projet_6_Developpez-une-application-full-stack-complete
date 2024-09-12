package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.TopicDTO;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.services.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicMapper topicMapper;

    public Map<String, List<TopicDTO>> getAllTopics(){

        List<Topic> themes =  topicRepository.findAll();

        return Map.of("topics", topicMapper.toDto(themes));
    }
}
