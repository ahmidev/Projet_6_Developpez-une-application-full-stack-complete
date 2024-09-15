package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.SubscriptionAlreadyExistsException;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.SubscriptionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


/**
 * Implémentation du service de gestion des abonnements aux sujets (topics).
 * Cette classe permet la création et la suppression d'abonnements pour les utilisateurs.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final UserMapper userMapper;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param subscriptionRepository le repository pour gérer les abonnements
     * @param userRepository le repository pour gérer les utilisateurs
     * @param topicRepository le repository pour gérer les sujets (topics)
     * @param userMapper le mapper pour convertir les entités utilisateur en DTO
     */
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository, TopicRepository topicRepository, UserMapper userMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.userMapper = userMapper;
    }


    /**
     * Crée un nouvel abonnement pour un utilisateur à un sujet spécifique.
     *
     * @param subscriptionDto les informations de l'abonnement à créer
     * @return un DTO de l'utilisateur mis à jour après la création de l'abonnement
     * @throws ResourceNotFoundException si l'utilisateur ou le sujet n'existent pas
     * @throws SubscriptionAlreadyExistsException si l'utilisateur est déjà abonné au sujet
     */
    @Transactional
    @Override
    public UserDTO createSubscription(SubscriptionDTO subscriptionDto) {
        User user = userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + subscriptionDto.getUserId()));

        Topic theme = topicRepository.findById(subscriptionDto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id: " + subscriptionDto.getTopicId()));

        if (subscriptionRepository.existsByUserIdAndTopicId(subscriptionDto.getUserId(), subscriptionDto.getTopicId())) {
            throw new SubscriptionAlreadyExistsException("User already subscribed to this theme");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(theme);
        subscriptionRepository.saveAndFlush(subscription);

        return userMapper.toDto(user);
    }


    /**
     * Supprime l'abonnement d'un utilisateur à un sujet spécifique.
     *
     * @param userId l'ID de l'utilisateur
     * @param topicId l'ID du sujet (topic)
     * @return un DTO de l'utilisateur mis à jour après la suppression de l'abonnement
     * @throws ResourceNotFoundException si l'abonnement ou l'utilisateur n'existent pas
     */
    @Override
    public UserDTO deleteSubscription(Long userId, Long topicId) {
        Subscription subscription = subscriptionRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found for userId: " + userId + " and themeId: " + topicId));

        subscriptionRepository.delete(subscription);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return userMapper.toDto(user);
    }

}
