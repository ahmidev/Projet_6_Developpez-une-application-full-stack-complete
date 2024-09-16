package com.openclassrooms.mddapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "topic")
    @JsonBackReference
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "topic")
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();
}