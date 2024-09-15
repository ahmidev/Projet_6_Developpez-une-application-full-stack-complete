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


/**
 * Implémentation du service de gestion des sujets (topics).
 * Cette classe fournit des méthodes pour récupérer tous les sujets disponibles sous forme de DTOs.
 */
@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;


    /**
     * Constructeur avec injection des dépendances.
     *
     * @param topicRepository le repository permettant l'accès aux données des sujets
     * @param topicMapper le mapper pour convertir les entités Topic en DTO
     */
    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }


    /**
     * Récupère tous les sujets (topics) disponibles et les renvoie sous forme de DTOs.
     *
     * @return une map contenant la liste des sujets sous forme de DTOs
     */
    public Map<String, List<TopicDTO>> getAllTopics(){

        List<Topic> themes =  topicRepository.findAll();

        return Map.of("topics", topicMapper.toDto(themes));
    }
}
