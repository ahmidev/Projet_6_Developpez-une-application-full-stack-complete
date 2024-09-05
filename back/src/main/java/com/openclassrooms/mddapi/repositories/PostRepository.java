package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM article WHERE theme_id IN (SELECT theme_id FROM subscription WHERE user_id = :userId)", nativeQuery = true)
    List<Post> getPostsByUserID(@Param("userId") Long userId);
}