package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(
            value = "SELECT * FROM subscription s WHERE s.topic_id = :themeId AND s.user_id = :userId",
            nativeQuery = true)
    Optional<Subscription> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("themeId") Long themeId);
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
}