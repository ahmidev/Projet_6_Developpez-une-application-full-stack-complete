package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Post, Long> {
}