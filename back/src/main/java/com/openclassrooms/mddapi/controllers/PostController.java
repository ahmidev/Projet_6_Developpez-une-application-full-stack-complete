package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.services.interfaces.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des articles.
 * Fournit des endpoints pour récupérer, créer et gérer les articles.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * Constructeur du contrôleur PostController, injectant le service de gestion des articles.
     *
     * @param postService service de gestion des articles
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Endpoint pour récupérer tous les articles liés à l'utilisateur connecté par son ID.
     * Cette méthode retourne une liste des articles auxquels l'utilisateur est abonné.
     *
     * @param userId l'ID de l'utilisateur
     * @return une réponse contenant la liste des articles avec un statut HTTP 200 OK
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getAllUsersSubscribedPosts(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getAllUsersSubscribedPosts(userId);
        return ResponseEntity.ok(posts);
    }

    /**
     * Endpoint pour créer un nouvel article.
     * Cette méthode permet de poster un nouvel article en fournissant les informations nécessaires dans le corps de la requête.
     *
     * @param createPost l'objet contenant les informations nécessaires pour créer l'article
     * @return une réponse contenant l'article créé avec un statut HTTP 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDTO> createAnPost(@RequestBody CreatePostDTO createPost) {
        PostDTO postDTO = postService.createAnPost(createPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
    }

    /**
     * Endpoint pour récupérer un article par son ID.
     * Cette méthode permet de récupérer un article spécifique en fonction de son identifiant.
     *
     * @param postId l'ID de l'article à récupérer
     * @return une réponse contenant l'article trouvé avec un statut HTTP 200 OK
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostByID(@PathVariable Long postId) {
        PostDTO postDTO = postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
}
