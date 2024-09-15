package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.TopicDTO;

import java.util.List;
import java.util.Map;

/**
 * Interface pour le service de gestion des sujets (topics).
 * Définit la méthode pour récupérer tous les sujets disponibles.
 */
public interface TopicService {

    /**
     * Récupère tous les sujets disponibles et les renvoie sous forme de DTOs.
     *
     * @return une map contenant la liste des sujets sous forme de DTOs
     */
    Map<String, List<TopicDTO>> getAllTopics();
}
