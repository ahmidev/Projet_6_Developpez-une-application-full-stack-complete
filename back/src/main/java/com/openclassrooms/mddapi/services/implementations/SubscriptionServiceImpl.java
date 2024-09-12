package com.openclassrooms.mddapi.services.implementations;

import com.openclassrooms.mddapi.dtos.SubscriptionDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.SubscriptionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDTO createSubscription(SubscriptionDTO subscriptionDto) {
        Optional<User> optUser = userRepository.findById(subscriptionDto.getUserId());
        Optional<Topic> optTheme = topicRepository.findById(subscriptionDto.getTopicId());

        if (optUser.isPresent() && optTheme.isPresent()) {
            User user = optUser.get();
            Topic theme = optTheme.get();

            if (subscriptionRepository.existsByUserIdAndTopicId(subscriptionDto.getUserId(), subscriptionDto.getTopicId())) {
                throw new RuntimeException("Utilisateur déjà abonné à ce thème");
            }

            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setTopic(theme);
            subscriptionRepository.saveAndFlush(subscription);

            user.getSubscriptions();
            user.getPosts();
            user.getComments();

            return userMapper.toDto(user);
        } else {
            throw new RuntimeException("et Mince");
        }
    }

    @Override
    public void deleteSubscription(Long userId, Long themeId) {
        subscriptionRepository.findByUserIdAndTopicId(userId, themeId)
                .ifPresentOrElse(user -> {
                    subscriptionRepository.delete(user);
                }, () -> {
                    throw new RuntimeException();
                });
    }
}
