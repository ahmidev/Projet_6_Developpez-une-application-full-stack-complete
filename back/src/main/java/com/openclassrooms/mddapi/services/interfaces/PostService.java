package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;

import java.util.List;

/**
 * Interface pour le service de gestion des posts.
 * Définit les méthodes pour créer, récupérer et gérer les posts.
 */
public interface PostService {

    /**
     * Récupère tous les posts des sujets auxquels l'utilisateur est abonné.
     *
     * @param userId l'ID de l'utilisateur
     * @return une liste de DTOs représentant les posts
     */
    List<PostDTO> getAllUsersSubscribedPosts(Long userId);

    /**
     * Crée un nouveau post avec les informations fournies.
     *
     * @param createPost les informations du post à créer
     * @return le DTO du post créé
     */
    PostDTO createAnPost(CreatePostDTO createPost);

    /**
     * Récupère un post par son ID.
     *
     * @param postId l'ID du post à récupérer
     * @return le DTO du post correspondant
     */
    PostDTO getPostById(Long postId);
}
