package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@Data
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

<<<<<<< Updated upstream
=======
    @Lob
    @Column(columnDefinition = "LONGTEXT")
>>>>>>> Stashed changes
    private String content;
    @ManyToOne
    @JoinColumn(name="owner_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="theme_id", nullable = false)
    private Topic theme;

    @CreationTimestamp
    private Instant created_At;


}