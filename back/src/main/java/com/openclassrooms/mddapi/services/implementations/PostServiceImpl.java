package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.CreatePostDTO;
import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.PostService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


/**
 * Implémentation du service de gestion des posts.
 * Cette classe fournit des méthodes pour récupérer, créer et gérer les posts des utilisateurs.
 */
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param postRepository le repository pour gérer les posts
     * @param userRepository le repository pour gérer les utilisateurs
     * @param topicRepository le repository pour gérer les sujets (topics)
     * @param postMapper le mapper pour convertir les entités Post en DTO
     */
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, TopicRepository topicRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.postMapper = postMapper;
    }

    /**
     * Récupère tous les posts des sujets auxquels l'utilisateur est abonné.
     *
     * @param userId l'ID de l'utilisateur
     * @return une liste de DTOs représentant les posts
     */
    public List<PostDTO> getAllUsersSubscribedPosts(Long userId){
    List<Post> posts = postRepository.findByTopic_Subscriptions_UserId(userId);
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
    return postMapper.toDto(posts);
    }


    /**
     * Crée un nouveau post pour un utilisateur dans un sujet spécifique.
     *
     * @param createPost les informations du post à créer
     * @return le DTO du post créé
     * @throws RuntimeException si l'utilisateur ou le sujet n'existent pas
     */
    @Transactional
    public PostDTO createAnPost(CreatePostDTO createPost){
        Post post = new Post();
        post.setContent(createPost.getContent());
        post.setTitle(createPost.getTitle());

        Optional<User> optUser = userRepository.findById(createPost.getUserId());
        Optional<Topic> optTheme = topicRepository.findById(createPost.getTopicId());
        if(optTheme.isPresent() && optUser.isPresent() ){
            post.setTopic(optTheme.get());
            post.setUser(optUser.get());
            postRepository.saveAndFlush(post);
            return postMapper.toDto(post);
        }

        throw new RuntimeException("theme inexistant");
    }


    /**
     * Récupère un post par son ID.
     *
     * @param postId l'ID du post à récupérer
     * @return le DTO du post correspondant
     * @throws RuntimeException si le post n'existe pas
     */
    @Transactional
    public PostDTO getPostById(Long postId){
        Optional<Post> optPost = postRepository.findById(postId);

        if(optPost.isPresent()){
            Post post = optPost.get();
            post.getComments();
            post.getTopic();
            post.getUser();
            return postMapper.toDto(post);
        }

        throw new RuntimeException("Cet post n'existe pas en Bdd");
    }
}
