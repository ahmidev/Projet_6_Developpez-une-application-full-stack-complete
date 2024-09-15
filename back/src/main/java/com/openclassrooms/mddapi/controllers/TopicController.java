package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicDTO;
import com.openclassrooms.mddapi.services.interfaces.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des thèmes.
 * Fournit des endpoints pour récupérer tous les thèmes disponibles.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    /**
     * Constructeur du contrôleur TopicController, injectant le service de gestion des thèmes.
     *
     * @param topicService service de gestion des thèmes
     */
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Endpoint pour récupérer tous les thèmes.
     * Cette méthode renvoie la liste de tous les thèmes disponibles dans un format JSON.
     *
     * @return ResponseEntity contenant une map avec la clé "topics" et la liste des thèmes, ainsi qu'un statut HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<Map<String, List<TopicDTO>>> getAllThemes() {
        Map<String, List<TopicDTO>> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
}

